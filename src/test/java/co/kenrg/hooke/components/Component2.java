package co.kenrg.hooke.components;

import co.kenrg.hooke.annotations.Autowired;
import co.kenrg.hooke.annotations.Component;
import co.kenrg.hooke.annotations.Qualifier;

@Component
public class Component2 {
    private Component3 component3;

    public Component2(Component3 component3) {
        this.component3 = component3;
    }

    @Autowired
    public void setup(@Qualifier("superSecretApiKey") String apiKey) {
        System.out.printf("API key: %s\n", apiKey);
    }

    public String getMessage() {
        return component3.getGreeting();
    }
}
