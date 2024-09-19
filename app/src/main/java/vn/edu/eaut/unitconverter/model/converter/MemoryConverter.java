package vn.edu.eaut.unitconverter.model.converter;

//import
import android.content.Context;
import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.model.ConversionUnit;

public class MemoryConverter extends Converter{

    public MemoryConverter(Context context) {
        super(context);
    }

    @Override
    public void load() throws Exception {
        registerUnit(new ConversionUnit(
                context.getString(R.string.bit),
                0.125,
                context.getString(R.string.bit_symbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.memorybyte),
                1.0,
                context.getString(R.string.memorybytesymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.kilobyte),
                1000.0,
                context.getString(R.string.kilobytesymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.megabyte),
                1000000.0,
                context.getString(R.string.megabytesymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.gigabyte),
                1000000000.0,
                context.getString(R.string.gigabytesymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.terabyte),
                1000000000000.0,
                context.getString(R.string.terabytesymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.petabyte),
                1000000000000000.0,
                context.getString(R.string.petabytesymbol)
        ));
    }
}
