package vn.edu.eaut.unitconverter.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Categories {

    private static final List<ConverterCategory> INSTANCE = Arrays.asList(ConverterCategory.values());

    private Categories() {}

    public static <T> List<T> mapTo(Function<ConverterCategory, T> mapper) {
        List<T> result = new ArrayList<>();
        for (ConverterCategory category : INSTANCE) {
            result.add(mapper.apply(category));
        }
        return result;
    }

    public static void forEach(Consumer<ConverterCategory> consumer) {
        INSTANCE.forEach(consumer);
    }

    public static ConverterCategory get(int index) {
        return INSTANCE.get(index);
    }

    public static int size() {
        return INSTANCE.size();
    }
}
