package com.brayan.guardianfinanciero.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleTestController {

    @GetMapping("/admin/test")
    public ResponseEntity<String> adminTest() {
        return ResponseEntity.ok("Acceso permitido para ADMIN");
    }

    @GetMapping("/analyst/test")
    public ResponseEntity<String> analystTest() {
        return ResponseEntity.ok("Acceso permitido para ADMIN o ANALYST");
    }

    @GetMapping("/audit/test")
    public ResponseEntity<String> auditTest() {
        return ResponseEntity.ok("Acceso permitido para ADMIN o AUDITOR");
    }
}
