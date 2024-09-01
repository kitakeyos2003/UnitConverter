package vn.edu.eaut.unitconverter.converter;


public class ConvertResult {
    public static ConvertResult EMPTY =  new ConvertResult("");
    private final String result;

    public ConvertResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
