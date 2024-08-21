package vn.edu.eaut.unitconverter.model.converter;

import android.content.Context;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.model.ConversionUnit;

public class SpeedConverter extends Converter{
    public SpeedConverter(Context context) {
        super(context);
    }

    @Override
    public void load() throws Exception {
        registerUnit(new ConversionUnit(
                context.getString(R.string.metrespersecond),
                3.6,
                context.getString(R.string.mpssymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.kilometresperhour),
                1.0,
                context.getString(R.string.kmpssymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.milesperhour),
                1.609344,
                context.getString(R.string.miphsymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.footspersecond),
                1.09728,
                context.getString(R.string.ftpssymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.knots),
                1.852,
                context.getString(R.string.knotssymbol)
        ));
    }
}
