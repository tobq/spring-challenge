package org.akinyemi.tobi.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ChallengeApplication {
    /**
     * Application entry point
     *
     * @param args
     * @see org.akinyemi.tobi.challenge.api.CharacterController
     */
    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }
}
