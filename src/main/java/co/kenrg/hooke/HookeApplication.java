package co.kenrg.hooke;

import java.io.IOException;

import co.kenrg.hooke.application.Application;

public class HookeApplication {
    private static Application INSTANCE;

    public static void start(Class appClass) {
        try {
            HookeApplication.INSTANCE = new Application(appClass);
            INSTANCE.run();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
