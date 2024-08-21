package vn.edu.eaut.unitconverter.model.converter;

import android.content.Context;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.model.ConversionUnit;

public class TimeConverter extends Converter{
    public TimeConverter(Context context) {
        super(context);
    }

    @Override
    public void load() throws Exception {
        registerUnit(new ConversionUnit(
                context.getString(R.string.microsecond),
                0.000001,
                context.getString(R.string.microsecondsymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.millisecond),
                0.001,
                context.getString(R.string.millisecondsymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.second),
                1.0,
                context.getString(R.string.secondsymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.minute),
                60.0,
                context.getString(R.string.minutesymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.hour),
                3600.0,
                context.getString(R.string.hoursymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.day),
                86400.0,
                context.getString(R.string.daysymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.week),
                604800.0,
                context.getString(R.string.weeksymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.year),
                31557600.0,
                context.getString(R.string.yearsymbol)
        ));
    }
}
