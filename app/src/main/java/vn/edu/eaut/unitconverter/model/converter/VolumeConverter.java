package vn.edu.eaut.unitconverter.model.converter;

import android.content.Context;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.model.ConversionUnit;

public class VolumeConverter extends Converter{
    public VolumeConverter(Context context) {
        super(context);
    }

    @Override
    public void load() throws Exception {
        registerUnit(new ConversionUnit(
                context.getString(R.string.litre),
                0.001,
                context.getString(R.string.litresymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.cubicmetre),
                1.0,
                context.getString(R.string.metresymbol) + CUBIC_POSTFIX
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.millilitre),
                0.000001,
                context.getString(R.string.millilitresymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.gallon),
                0.00378541,
                context.getString(R.string.gallonsymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.barrel),
                0.158988,
                context.getString(R.string.barrelsymbol)
        ));
    }
}
