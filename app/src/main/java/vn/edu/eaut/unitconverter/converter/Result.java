package vn.edu.eaut.unitconverter.converter;


public abstract class Result {
    public static Result Empty =  Result.Empty;
    private final String result;

    public Result(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public static class Empty extends Result {
        public Empty() {
            super("");
        }
    }

    public static class Converted extends Result {
        public Converted(String result) {
            super(result);
        }
    }
}
