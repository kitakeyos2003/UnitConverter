package vn.edu.eaut.unitconverter.model.converter;

import android.content.Context;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.model.ConversionUnit;

public class LengthConverter extends Converter {

    public LengthConverter(Context context) {
        super(context);
    }

    @Override
    public void load() {
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.metre),
                        1.0,
                        context.getString(R.string.metresymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.millimetre),
                        0.001,
                        context.getString(R.string.millimetresymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.centimetre),
                        0.01,
                        context.getString(R.string.centimetresymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.decimetre),
                        0.1,
                        context.getString(R.string.decimetresymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.kilometre),
                        1000.0,
                        context.getString(R.string.kilometresymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.inch),
                        0.0254,
                        context.getString(R.string.inchsymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.mile),
                        1609.344,
                        context.getString(R.string.milesymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.foot),
                        0.3048,
                        context.getString(R.string.footsymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.yard),
                        0.9144,
                        context.getString(R.string.yardsymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.astronomicalunit),
                        149597870700.0,
                        context.getString(R.string.astronomicalunitsymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.parsec),
                        3.0856776e16,
                        context.getString(R.string.parsecsymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.lightyear),
                        9460730472580800.0,
                        context.getString(R.string.lightyearsymbol)
                )
        );
    }

}
