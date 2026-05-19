package com.lrvoley.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@Configuration
public class DbMigration {

    @Bean
    CommandLineRunner migrateSchema(DataSource ds) {
        return args -> {
            try (Connection con = ds.getConnection()) {
                DatabaseMetaData meta = con.getMetaData();
                ResultSet cols = meta.getColumns(null, null, "Entrenamientos", "descripcion");
                if (!cols.next()) {
                    con.createStatement().execute(
                        "ALTER TABLE Entrenamientos ADD COLUMN descripcion TEXT NULL"
                    );
                    System.out.println("Migración: columna 'descripcion' añadida a Entrenamientos");
                }
            } catch (Exception e) {
                System.err.println("Migración falló (puede que la columna ya exista): " + e.getMessage());
            }
        };
    }
}
