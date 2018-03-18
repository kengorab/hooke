package co.kenrg.hooke.components;

import co.kenrg.hooke.annotations.Autowired;
import co.kenrg.hooke.annotations.Component;
import co.kenrg.hooke.annotations.Qualifier;

@Component
public class Component3 {
    @Autowired @Qualifier("greeting") private String greeting;

    public String getGreeting() {
        return greeting;
    }
}
