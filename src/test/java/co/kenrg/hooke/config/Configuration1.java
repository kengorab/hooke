package co.kenrg.hooke.config;

import co.kenrg.hooke.annotations.Bean;
import co.kenrg.hooke.annotations.Configuration;
import co.kenrg.hooke.annotations.Qualifier;

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
}
