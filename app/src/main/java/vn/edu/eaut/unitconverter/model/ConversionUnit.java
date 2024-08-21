package vn.edu.eaut.unitconverter.model;

public class ConversionUnit {
    private final String name;
    private final double factor;
    private final String unitSymbol;

    public static final ConversionUnit EMPTY = new ConversionUnit("", 1.0, "");

    public ConversionUnit(String name, double factor, String unitSymbol) {
        this.name = name;
        this.factor = factor;
        this.unitSymbol = unitSymbol;
    }

    public String getName() {
        return name;
    }

    public double getFactor() {
        return factor;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }
}
