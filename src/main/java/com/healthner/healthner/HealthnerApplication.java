package com.healthner.healthner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HealthnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthnerApplication.class, args);
    }

}