package co.kenrg.hooke;

import co.kenrg.hooke.application.ApplicationContext;
import co.kenrg.hooke.components.Component1;

public class ExampleHookeApplication {
    public static void main(String[] args) {
        HookeApplication.start(ExampleHookeApplication.class);

        ApplicationContext ctx = HookeApplication.getApplicationContext();
        Component1 component1 = ctx.getBeanOfType(Component1.class);
        component1.printMessage();
    }
}
