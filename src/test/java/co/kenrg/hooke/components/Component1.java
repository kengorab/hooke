package co.kenrg.hooke.components;

import javax.annotation.PostConstruct;

import co.kenrg.hooke.annotations.Autowired;
import co.kenrg.hooke.annotations.Component;

@Component
public class Component1 {
    @Autowired private Component2 component2;

    @PostConstruct
    public void printMessage() {
        System.out.printf("Component1 says 'Hello'; Component2 says '%s'\n", component2.getMessage());
    }
}
