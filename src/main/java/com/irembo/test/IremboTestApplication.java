package com.irembo.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.irembo")
public class IremboTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(IremboTestApplication.class, args);
    }

}

