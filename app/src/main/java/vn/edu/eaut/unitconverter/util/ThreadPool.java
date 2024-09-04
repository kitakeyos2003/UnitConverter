package vn.edu.eaut.unitconverter.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private static final ExecutorService service = Executors.newFixedThreadPool(5);

    public static void execute(Runnable runnable) {
        service.execute(runnable);
    }
}
