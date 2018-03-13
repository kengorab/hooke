package co.kenrg.hooke.components;

import co.kenrg.hooke.annotations.Autowired;
import co.kenrg.hooke.annotations.Component;

@Component
public class Component1 {
    @Autowired private Component2 component2;

    public void printMessage() {
        System.out.printf("Component1 says 'Hello'; Component2 says '%s'\n", component2.getMessage());
    }
}
