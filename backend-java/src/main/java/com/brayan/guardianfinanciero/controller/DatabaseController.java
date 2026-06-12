package com.brayan.guardianfinanciero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/status")
    public Map<String, String> status() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return Map.of(
                    "status", "connected",
                    "database", "PostgreSQL",
                    "message", "Conexión a PostgreSQL configurada correctamente"
            );
        } catch (Exception e) {
            return Map.of(
                    "status", "error",
                    "database", "PostgreSQL",
                    "message", "Error de conexión: " + e.getMessage()
            );
        }
    }
}
