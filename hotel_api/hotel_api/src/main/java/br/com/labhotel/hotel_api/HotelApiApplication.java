package br.com.labhotel.hotel_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // <-- 1. ADICIONE ESTA IMPORTAÇÃO

// 2. ADICIONE ESTA ANOTAÇÃO
// Esta linha FORÇA o Spring a encontrar todas as suas
// pastas (config, service, controller, etc.)
@ComponentScan(basePackages = "br.com.labhotel.hotel_api") 
@SpringBootApplication
public class HotelApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelApiApplication.class, args);
    }

}