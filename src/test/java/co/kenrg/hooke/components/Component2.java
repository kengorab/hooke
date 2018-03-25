package co.kenrg.hooke.components;

import co.kenrg.hooke.annotations.Component;

@Component
public class Component2 {
    private Component3 component3;

    public Component2(Component3 component3) {
        this.component3 = component3;
    }

    public String getMessage() {
        return component3.getGreeting();
    }
}
