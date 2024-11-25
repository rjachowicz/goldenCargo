package com.goldencargo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GoldenCargoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoldenCargoApplication.class, args);
    }

}
