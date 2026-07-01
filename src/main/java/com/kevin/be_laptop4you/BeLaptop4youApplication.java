package com.kevin.be_laptop4you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeLaptop4youApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeLaptop4youApplication.class, args);
    }

}
