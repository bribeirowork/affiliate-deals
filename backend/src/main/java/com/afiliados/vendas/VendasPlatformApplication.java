package com.afiliados.vendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VendasPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendasPlatformApplication.class, args);
    }
}
