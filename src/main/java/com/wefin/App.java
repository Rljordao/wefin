package com.wefin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.wefin"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}