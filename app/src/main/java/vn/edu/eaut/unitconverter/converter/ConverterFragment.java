package vn.edu.eaut.unitconverter.converter;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;
import dev.chrisbanes.insetter.Insetter;
import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.databinding.FragmentConverterBinding;
import vn.edu.eaut.unitconverter.history.HistoryActivity;

@AndroidEntryPoint
public class ConverterFragment extends Fragment {
    private FragmentConverterBinding binding;

    private ConverterViewModel viewModel;

    public ConverterFragment() {
        super(R.layout.fragment_converter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ConverterViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentConverterBinding.bind(view);

        binding.toolbar.inflateMenu(R.menu.menu_converter);
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.save) {
                viewModel.saveResultToHistory(binding.display.getConvertData());
                return true;
            } else if (item.getItemId() == R.id.history) {
                startActivity(new Intent(requireContext(), HistoryActivity.class));
                return true;
            }
            return false;
        });

        binding.toolbar.setNavigationIcon(R.drawable.ic_menu);
        binding.toolbar.setNavigationOnClickListener(v -> viewModel.setDrawerOpened(true));

        binding.display.onTextChanged(text -> viewModel.convert(binding.display.getConvertData()));
        binding.display.setSpinnersCallback(() -> viewModel.convert(binding.display.getConvertData()));

        binding.keypad.setOnCopyListeners(longPress -> {
            ClipboardManager clipboardManager = ContextCompat.getSystemService(requireContext(), ClipboardManager.class);
            if (clipboardManager != null) {
                ClipData clipData = ClipData.newPlainText("Conversion result", binding.display.getCopyResult(longPress));
                clipboardManager.setPrimaryClip(clipData);
                Snackbar.make(binding.getRoot(), R.string.copy_result_toast, Snackbar.LENGTH_SHORT).show();
            }
        });
        if (binding.converterDisplayContainer != null) {
            Insetter.builder()
                    .setOnApplyInsetsListener((v, insets, initialState) -> {
                        int statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
                        v.setPadding(
                                v.getPaddingLeft(),
                                v.getPaddingTop() + statusBarInsets,
                                v.getPaddingRight(),
                                v.getPaddingBottom()
                        );
                    })
                    .applyToView(binding.converterDisplayContainer);
        }

        Insetter.builder()
                .setOnApplyInsetsListener((v, insets, initialState) -> {
                    int systemBarInsetsTop = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                    int systemBarInsetsBottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;

                    v.setPadding(
                            v.getPaddingLeft(),
                            v.getPaddingTop() + systemBarInsetsTop,
                            v.getPaddingRight(),
                            v.getPaddingBottom() + systemBarInsetsBottom
                    );
                })
                .applyToView(binding.keypad);

        binding.keypad.setBackspaceListeners(longPress -> {
            if (longPress) {
                binding.display.erase();
            } else {
                binding.display.removeLastDigit();
            }
        });

        binding.keypad.setOnPasteListener((a) -> {
            ClipboardManager clipboardManager = ContextCompat.getSystemService(requireContext(), ClipboardManager.class);
            if (clipboardManager != null && clipboardManager.hasPrimaryClip()) {
                ClipData clipData = clipboardManager.getPrimaryClip();
                if (clipData != null && clipData.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    binding.display.appendText(clipData.getItemAt(0).getText());
                }
            }
        });

        binding.keypad.setNumericListener(binding.display::appendText);

        viewModel.getBackgroundColor().observe(getViewLifecycleOwner(), colorRes -> {
            int color = ContextCompat.getColor(requireContext(), R.color.material_bluegrey_500);
            binding.background.setBackgroundColor(color);
        });

        viewModel.getTitle().observe(getViewLifecycleOwner(), binding.toolbar::setTitle);

        viewModel.getConverter().observe(getViewLifecycleOwner(), converter -> {
            binding.display.setUnits(converter.getUnits());
        });

        viewModel.getResult().observe(getViewLifecycleOwner(), result -> {
            binding.display.showResult(result.getResult());
        });
    }

}
