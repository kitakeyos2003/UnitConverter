package vn.edu.eaut.unitconverter.converter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.core.text.HtmlCompat;

import com.google.android.material.textfield.TextInputLayout;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.databinding.DisplayBinding;
import vn.edu.eaut.unitconverter.model.ConversionUnit;

import java.util.ArrayList;
import java.util.List;


public class ConverterDisplayView extends LinearLayout {
    private final DisplayBinding binding;
    private final List<ConversionUnit> conversionUnits = new ArrayList<>();

    private int sourceIndex = 0;
    private int resultIndex = 0;

    public ConverterDisplayView(Context context) {
        this(context, null);
    }

    public ConverterDisplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConverterDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ConverterDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOrientation(VERTICAL);
        binding = DisplayBinding.inflate(LayoutInflater.from(context), this);

        binding.fab.setOnClickListener(v -> setConvertData(getConvertData().swap()));

        binding.sourceValue.setShowSoftInputOnFocus(false);
        binding.resultValue.setShowSoftInputOnFocus(false);
    }

    public void onTextChanged(OnTextChangedListener watcher) {
        binding.sourceValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                watcher.onTextChanged(editable.toString());
            }
        });
    }

    public void showResult(String result) {
        binding.resultValue.setText(result);
        updateSuffixes();
    }

    public ConvertData getConvertData() {
        return new ConvertData(
                binding.sourceValue.getText().toString(),
                binding.resultValue.getText().toString(),
                sourceIndex,
                resultIndex
        );
    }

    public void setConvertData(ConvertData convertData) {
        binding.sourceValue.setText(convertData.getValue());
        binding.resultValue.setText(convertData.getResult());
        sourceIndex = convertData.getFrom();
        resultIndex = convertData.getTo();
        binding.sourceSpinner.setText(conversionUnits.get(sourceIndex).getName(), false);
        binding.resultSpinner.setText(conversionUnits.get(resultIndex).getName(), false);
        updateSuffixes();
    }

    public String getCopyResult(boolean withUnitSymbols) {
        StringBuilder builder = new StringBuilder(binding.resultValue.getText());
        if (withUnitSymbols) {
            if (binding.sourceValueContainer.getSuffixText() != null) {
                builder.append(binding.sourceValueContainer.getSuffixText());
            }
        }
        return builder.toString();
    }

    private void setSpinnerAdapter(ArrayAdapter<String> adapter, boolean isDifferent) {
        binding.sourceSpinner.setAdapter(adapter);
        binding.resultSpinner.setAdapter(adapter);

        if (!adapter.isEmpty()) {
            sourceIndex = 0;
            resultIndex = isDifferent ? 1 : 0;
            binding.sourceSpinner.setText(adapter.getItem(sourceIndex), false);
            binding.resultSpinner.setText(adapter.getItem(resultIndex), false);
        } else {
            binding.sourceSpinner.setEnabled(false);
            binding.resultSpinner.setEnabled(false);
        }
    }

    public void setUnits(List<ConversionUnit> units) {
        conversionUnits.clear();
        conversionUnits.addAll(units);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                convertUnitNames(units)
        );
        setSpinnerAdapter(adapter, units.size() > 1);
    }

    private List<String> convertUnitNames(List<ConversionUnit> units) {
        List<String> names = new ArrayList<>();
        for (ConversionUnit unit : units) {
            names.add(unit.getName());
        }
        return names;
    }

    public void erase() {
        View revealView = new View(getContext());
        revealView.setTop(-getTop());
        revealView.setBottom(getBottom());
        revealView.setLeft(getLeft());
        revealView.setRight(getRight());
        revealView.setBackgroundColor(getContext().getColor(R.color.colorSecondary));

        ViewGroupOverlay groupOverlay = ((ViewGroup) getParent()).getOverlay();
        groupOverlay.add(revealView);

        int revealCenterX = getWidth();
        int revealCenterY = getHeight() + getTop();
        float revealRadius = (float) Math.sqrt(revealCenterX * revealCenterX + revealCenterY * revealCenterY);

        Animator revealAnimator = ViewAnimationUtils.createCircularReveal(
                revealView,
                revealCenterX,
                revealCenterY,
                0f,
                revealRadius
        );
        revealAnimator.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));

        Animator alphaAnimator = ObjectAnimator.ofFloat(revealView, ALPHA, 0f);
        alphaAnimator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                clear();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(revealAnimator).before(alphaAnimator);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                groupOverlay.remove(revealView);
            }
        });
        animatorSet.start();
    }

    public void clear() {
        binding.resultValue.getText().clear();
        binding.sourceValue.getText().clear();
    }

    public void appendText(CharSequence text) {
        binding.sourceValue.append(text);
    }

    public void removeLastDigit() {
        String textString = binding.sourceValue.getText().toString();
        if (!textString.isEmpty()) {
            binding.sourceValue.setText(textString.substring(0, textString.length() - 1));
            binding.sourceValue.selectAll();
        }
    }

    public void setSpinnersCallback(Runnable callback) {
        binding.sourceSpinner.setOnItemClickListener((parent, view, position, id) -> {
            sourceIndex = position;
            callback.run();
        });
        binding.resultSpinner.setOnItemClickListener((parent, view, position, id) -> {
            resultIndex = position;
            callback.run();
        });
    }

    private void updateSuffixes() {
        setHtmlSuffixText(binding.sourceValueContainer, conversionUnits.get(sourceIndex).getUnitSymbol());
        setHtmlSuffixText(binding.resultValueContainer, conversionUnits.get(resultIndex).getUnitSymbol());
    }

    private void setHtmlSuffixText(TextInputLayout textInputLayout, String text) {
        textInputLayout.setSuffixText(text);
        textInputLayout.getSuffixTextView().setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }
}
