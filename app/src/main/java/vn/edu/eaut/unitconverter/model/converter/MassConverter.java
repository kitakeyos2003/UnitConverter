package vn.edu.eaut.unitconverter.model.converter;

import android.content.Context;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.model.ConversionUnit;

public class MassConverter extends Converter {

    public MassConverter(Context context) {
        super(context);
    }

    @Override
    public void load() {
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.kilogram),
                        1.0,
                        context.getString(R.string.kilogramsymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.gram),
                        0.001,
                        context.getString(R.string.gramsymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.milligram),
                        0.0000001,
                        context.getString(R.string.milligramsymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.hundredweight),
                        100.0,
                        context.getString(R.string.hundredweightsymbol)
                )
        );
        registerUnit(
                new ConversionUnit(
                        context.getString(R.string.tonne),
                        1000.0,
                        context.getString(R.string.tonnesymbol)
                )
        );
    }

}
