package com.gcsdevs.livros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.gcsdevs.livros")
public class LivrosApplication {
    public static void main(String[] args) {
        SpringApplication.run(LivrosApplication.class, args);
    }
}
