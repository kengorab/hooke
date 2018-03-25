package co.kenrg.hooke.config;

import co.kenrg.hooke.annotations.Bean;
import co.kenrg.hooke.annotations.Configuration;
import co.kenrg.hooke.annotations.Qualifier;
import co.kenrg.hooke.components.Component2;
import co.kenrg.hooke.components.Component3;

@Configuration
public class Configuration1 {

    @Bean
    public String superSecretApiKey() {
        return "asoiudhfjkn";
    }

    @Bean
    public String exclamation() {
        return "!";
    }

    @Bean
    public String greeting(@Qualifier("exclamation") String exclamation) {
        return "Howdy" + exclamation;
    }

    @Bean
    public Component2 component2(Component3 component3) {
        return new Component2(component3);
    }
}
