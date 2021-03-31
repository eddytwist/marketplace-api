package com.edik.car.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class InitApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.edik.car.api.InitApplication.class, args);
    }
}
