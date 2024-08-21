package vn.edu.eaut.unitconverter.converter;


public class ConvertData {
    private final String value;
    private final String result;
    private final int from;
    private final int to;

    public ConvertData(String value, String result, int from, int to) {
        this.value = value;
        this.result = result;
        this.from = from;
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public String getResult() {
        return result;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public ConvertData swap() {
        return new ConvertData(result, value, to, from);
    }
}
