package com.mic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class SpringBootProductsMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootProductsMainApplication.class, args);

    }

    @Bean
    public CommandLineRunner run(DataSource datasource) {
        return args -> {
            try (Connection connection = datasource.getConnection()) {
                if (connection != null) {
                    System.out.println("Conexión exitosa con la base de datos.");
                } else {
                    System.out.println("Ha habido un problema al conectarse a la base de datos.");
                }
            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            }
        };
    }
}