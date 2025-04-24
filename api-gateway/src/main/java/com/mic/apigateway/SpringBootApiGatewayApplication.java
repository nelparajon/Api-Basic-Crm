package com.mic.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class }) //evitamos que sprinboot.jpa busque una url de base de datos en el properties
public class SpringBootApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApiGatewayApplication.class, args);
    }
}