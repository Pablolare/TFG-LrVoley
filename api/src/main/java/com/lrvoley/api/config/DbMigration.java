package com.lrvoley.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DbMigration {

    @Bean
    CommandLineRunner migrateSchema(DataSource ds) {
        return args -> {
            try (Connection con = ds.getConnection()) {
                con.createStatement().execute(
                    "ALTER TABLE Entrenamientos ADD COLUMN IF NOT EXISTS descripcion TEXT NULL"
                );
                System.out.println("Migración OK: columna 'descripcion' verificada en Entrenamientos");
            } catch (Exception e) {
                System.err.println("Migración: " + e.getMessage());
            }
        };
    }
}
