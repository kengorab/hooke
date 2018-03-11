package co.kenrg.hooke.components;

import co.kenrg.hooke.annotations.Autowired;
import co.kenrg.hooke.annotations.Component;

@Component
public class Component1 {
    @Autowired private Component2 component2;
}
