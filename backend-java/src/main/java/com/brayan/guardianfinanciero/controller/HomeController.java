package com.brayan.guardianfinanciero.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "application", "Guardián Financiero IA",
                "description", "Plataforma inteligente antifraude para análisis de transacciones financieras",
                "status", "running",
                "module", "Backend Java Spring Boot"
        );
    }
}
