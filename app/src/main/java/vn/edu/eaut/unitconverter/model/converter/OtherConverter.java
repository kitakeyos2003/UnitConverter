package vn.edu.eaut.unitconverter.model.converter;

import android.content.Context;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.model.ConversionUnit;

public class OtherConverter extends Converter{
    public OtherConverter(Context context) {
        super(context);
    }

    @Override
    public void load() throws Exception {
        registerUnit(new ConversionUnit(
                context.getString(R.string.pico),
                0.000000000001,
                toExp("-12")
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.nano),
                0.000000001,
                toExp("-9")
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.micro),
                0.000001,
                toExp("-6")
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.milli),
                0.001,
                toExp("-3")
        ));
        registerUnit(ConversionUnit.EMPTY);
        registerUnit(new ConversionUnit(
                context.getString(R.string.kilo),
                1000.0,
                toExp("3")
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.mega),
                1000000.0,
                toExp("6")
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.giga),
                1000000000.0,
                toExp("9")
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.tera),
                1000000000000.0,
                toExp("12")
        ));
    }

    private String toExp(String exponent) {
        //×10<sup><small>$this</small></sup>
        return "10^" + exponent;
    }
}
