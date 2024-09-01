package vn.edu.eaut.unitconverter.model.converter;


import android.content.Context;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import vn.edu.eaut.unitconverter.model.ConversionUnit;

public abstract class Converter {
    protected final Context context;
    private final List<ConversionUnit> conversionUnits = new ArrayList<>();

    public Converter(Context context) {
        this.context = context;
    }

    public List<ConversionUnit> getUnits() {
        return new ArrayList<>(conversionUnits);
    }

    public void registerUnit(ConversionUnit unit) {
        conversionUnits.add(unit);
    }

    public void sortUnitsWith(Comparator<ConversionUnit> comparator) {
        conversionUnits.sort(comparator);
    }

    public ConversionUnit get(int index) {
        return conversionUnits.get(index);
    }

    public abstract void load() throws Exception;

    public String convert(String inputValue, int inputValueType, int outputValueType) throws ArithmeticException {
        BigDecimal source = new BigDecimal(inputValue, MC);
        BigDecimal sourceFactor = BigDecimal.valueOf(conversionUnits.get(inputValueType).getFactor());
        BigDecimal resultFactor = BigDecimal.valueOf(conversionUnits.get(outputValueType).getFactor());
        BigDecimal result = source.multiply(sourceFactor).divide(resultFactor, MC);
        return result.stripTrailingZeros().toPlainString();
    }

    public static final MathContext MC = new MathContext(9, RoundingMode.HALF_UP);
    public static final String SQUARE_POSTFIX = "²";
    public static final String CUBIC_POSTFIX = "³";


}

