package vn.edu.eaut.unitconverter.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class Categories {

    public static final List<ConverterCategory> INSTANCE = Arrays.asList(ConverterCategory.values());

    public static <T> List<T> mapTo(Function<ConverterCategory, T> mapper) {
        List<T> result = new ArrayList<>();
        for (ConverterCategory category : INSTANCE) {
            result.add(mapper.apply(category));
        }
        return result;
    }
}
