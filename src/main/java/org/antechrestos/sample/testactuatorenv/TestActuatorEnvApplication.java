package org.antechrestos.sample.testactuatorenv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@SpringBootApplication
@EnableConfigurationProperties(TestActuatorEnvApplication.MyProperties.class)
public class TestActuatorEnvApplication {


    private static final Logger LOGGER = LoggerFactory.getLogger(TestActuatorEnvApplication.class);

    public static void main(String [] args){
        SpringApplication.run(TestActuatorEnvApplication.class, args);
    }

    @ConfigurationProperties("my.properties")
    public record MyProperties(int withDashes, int withoutDashes){}

    @RestController
    public static class DisplayPropertiesController {


        private final MyProperties myProperties;

        public DisplayPropertiesController(MyProperties myProperties) {
            this.myProperties = myProperties;
        }

        @GetMapping("/withDashes")
        public Mono<Integer> withDashes(){
            LOGGER.info("my.properties.with-dashes={}", myProperties.withDashes());
            return Mono.just(myProperties.withDashes());
        }

        @GetMapping("/withoutDashes")
        public Mono<Integer> withoutDashes(){
            LOGGER.info("my.properties.with-dashes={}", myProperties.withoutDashes());
            return Mono.just(myProperties.withoutDashes());
        }
    }
}
