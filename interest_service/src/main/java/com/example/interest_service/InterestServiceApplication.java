package com.example.interest_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class InterestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterestServiceApplication.class, args);
    }

}
