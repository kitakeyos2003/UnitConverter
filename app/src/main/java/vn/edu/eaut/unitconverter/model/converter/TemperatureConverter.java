package vn.edu.eaut.unitconverter.model.converter;

import android.content.Context;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.model.ConversionUnit;

public class TemperatureConverter extends Converter {
    public TemperatureConverter(Context context) {
        super(context);
    }

    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_EVEN);

    public void load() {
        registerUnit(new ConversionUnit(
                context.getString(R.string.kelvin),
                1.0,
                context.getString(R.string.kelvinsymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.celsius),
                1.0,
                context.getString(R.string.celsiussymbol)
        ));
        registerUnit(new ConversionUnit(
                context.getString(R.string.fahrenheit),
                1.0,
                context.getString(R.string.fahrenheitsymbol)
        ));
    }

    public String convert(String inputValue, int inputValueType, int outputValueType) throws ArithmeticException {
        BigDecimal sourceValue = new BigDecimal(inputValue, MC);
        return inputValueTypeToTemperatureUnit(inputValueType)
                .convertTo(inputValueTypeToTemperatureUnit(outputValueType), sourceValue)
                .stripTrailingZeros()
                .toPlainString();
    }

    private TemperatureUnit inputValueTypeToTemperatureUnit(int index) throws ArithmeticException {
        switch (index) {
            case TemperatureUnit.KELVIN_INDEX:
                return TemperatureUnit.KELVIN;
            case TemperatureUnit.CELSIUS_INDEX:
                return TemperatureUnit.CELSIUS;
            case TemperatureUnit.FAHRENHEIT_INDEX:
                return TemperatureUnit.FAHRENHEIT;
            default:
                throw new ArithmeticException("Unknown temperature unit index: " + index);
        }
    }

    private abstract static class TemperatureUnit {
        static final int KELVIN_INDEX = 0;
        static final int CELSIUS_INDEX = 1;
        static final int FAHRENHEIT_INDEX = 2;

        abstract BigDecimal convertTo(TemperatureUnit unit, BigDecimal x);

        static final TemperatureUnit KELVIN = new TemperatureUnit() {
            @Override
            BigDecimal convertTo(TemperatureUnit unit, BigDecimal x) {
                if (unit == KELVIN) {
                    return x;
                } else if (unit == CELSIUS) {
                    return x.add(MIN_KELVIN);
                } else if (unit == FAHRENHEIT) {
                    return CELSIUS.convertTo(FAHRENHEIT, x.add(MIN_KELVIN));
                } else {
                    throw new ArithmeticException("Unknown temperature unit");
                }
            }
        };

        static final TemperatureUnit CELSIUS = new TemperatureUnit() {
            @Override
            BigDecimal convertTo(TemperatureUnit unit, BigDecimal x) {
                if (unit == KELVIN) {
                    return x.subtract(MIN_KELVIN);
                } else if (unit == CELSIUS) {
                    return x;
                } else if (unit == FAHRENHEIT) {
                    return x.multiply(FAHRENHEIT_MULTIPLIER).add(FAHRENHEIT_OFFSET);
                } else {
                    throw new ArithmeticException("Unknown temperature unit");
                }
            }
        };

        static final TemperatureUnit FAHRENHEIT = new TemperatureUnit() {
            @Override
            BigDecimal convertTo(TemperatureUnit unit, BigDecimal x) {
                if (unit == KELVIN) {
                    return convertTo(CELSIUS, x).subtract(MIN_KELVIN);
                } else if (unit == CELSIUS) {
                    return (x.subtract(FAHRENHEIT_OFFSET)).divide(FAHRENHEIT_MULTIPLIER, MC);
                } else if (unit == FAHRENHEIT) {
                    return x;
                } else {
                    throw new ArithmeticException("Unknown temperature unit");
                }
            }
        };

        private static final BigDecimal MIN_KELVIN = new BigDecimal("-273.15", MC);
        private static final BigDecimal FAHRENHEIT_OFFSET = new BigDecimal("32", MC);
        private static final BigDecimal FAHRENHEIT_MULTIPLIER = new BigDecimal("1.8", MC);
    }
}
