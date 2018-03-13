package co.kenrg.hooke;

import java.io.IOException;

import co.kenrg.hooke.application.Application;
import co.kenrg.hooke.application.ApplicationContext;

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

    public static ApplicationContext getApplicationContext() {
        return INSTANCE.applicationContext;
    }
}
