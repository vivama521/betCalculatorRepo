package it.betcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BetCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BetCalculatorApplication.class, args);
    }
}
