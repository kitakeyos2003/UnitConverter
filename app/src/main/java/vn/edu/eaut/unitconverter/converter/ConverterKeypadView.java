package vn.edu.eaut.unitconverter.converter;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.databinding.KeypadBinding;

public class ConverterKeypadView extends LinearLayout {
    public interface ActionListener {
        void click(boolean isLongClick);
    }

    public interface NumberListener {
        void click(String digit);
    }

    private final Paint paint = new Paint();
    private final Rect rect = new Rect();

    private final Button mButton1;
    private final Button mButton2;
    private final Button mButton3;
    private final Button mButton4;
    private final Button mButton5;
    private final Button mButton6;
    private final Button mButton7;
    private final Button mButton8;
    private final Button mButton9;
    private final Button mButton0;
    private final Button mButtonDot;
    private final Button mButtonMinus;
    private final Button mButtonBackspace;
    private final ImageButton mButtonCopy;
    private final ImageButton mButtonPaste;

    public ConverterKeypadView(Context context) {
        this(context, null);
    }

    public ConverterKeypadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConverterKeypadView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ConverterKeypadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOrientation(HORIZONTAL);
        paint.setColor(context.getColor(R.color.keypad_background_color));

        KeypadBinding binding = KeypadBinding.inflate(LayoutInflater.from(context), this);
        mButton1 = binding.button1;
        mButton2 = binding.button2;
        mButton3 = binding.button3;
        mButton4 = binding.button4;
        mButton5 = binding.button5;
        mButton6 = binding.button6;
        mButton7 = binding.button7;
        mButton8 = binding.button8;
        mButton9 = binding.button9;
        mButton0 = binding.button0;
        mButtonDot = binding.buttonDot;
        mButtonMinus = binding.buttonMinus;
        mButtonBackspace = binding.buttonBackspace;
        mButtonCopy = binding.buttonCopy;
        mButtonPaste = binding.buttonPaste;
    }

    public void setOnCopyListeners(ActionListener listener) {
        setActionListener(mButtonCopy, listener);
    }

    public void setOnPasteListener(OnClickListener listener) {
        mButtonPaste.setOnClickListener(listener);
    }

    public void setBackspaceListeners(ActionListener listener) {
        setActionListener(mButtonBackspace, listener);
    }

    public void setNumericListener(NumberListener listener) {
        OnClickListener clickListener = v -> listener.click(mapper.get(v.getId()));
        mButton1.setOnClickListener(clickListener);
        mButton2.setOnClickListener(clickListener);
        mButton3.setOnClickListener(clickListener);
        mButton4.setOnClickListener(clickListener);
        mButton5.setOnClickListener(clickListener);
        mButton6.setOnClickListener(clickListener);
        mButton7.setOnClickListener(clickListener);
        mButton8.setOnClickListener(clickListener);
        mButton9.setOnClickListener(clickListener);
        mButton0.setOnClickListener(clickListener);
        mButtonDot.setOnClickListener(clickListener);
        mButtonMinus.setOnClickListener(clickListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.set(getWidth() - mButtonPaste.getWidth() - getPaddingRight(), 0, getWidth(), getHeight());
        canvas.drawRect(rect, paint);
    }

    private void setActionListener(View view, ActionListener listener) {
        view.setOnClickListener(v -> listener.click(false));
        view.setOnLongClickListener(v -> {
            listener.click(true);
            return true;
        });
    }

    private static final SparseArray<String> mapper = new SparseArray<>(10);

    static {
        mapper.append(R.id.button0, "0");
        mapper.append(R.id.button1, "1");
        mapper.append(R.id.button2, "2");
        mapper.append(R.id.button3, "3");
        mapper.append(R.id.button4, "4");
        mapper.append(R.id.button5, "5");
        mapper.append(R.id.button6, "6");
        mapper.append(R.id.button7, "7");
        mapper.append(R.id.button8, "8");
        mapper.append(R.id.button9, "9");
        mapper.append(R.id.button_dot, ".");
        mapper.append(R.id.button_minus, "-");
    }
}
