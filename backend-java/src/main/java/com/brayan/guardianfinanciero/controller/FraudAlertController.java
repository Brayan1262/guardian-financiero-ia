package com.brayan.guardianfinanciero.controller;

import com.brayan.guardianfinanciero.dto.alert.FraudAlertCommentRequest;
import com.brayan.guardianfinanciero.dto.alert.FraudAlertResponse;
import com.brayan.guardianfinanciero.dto.alert.FraudAlertStatusUpdateRequest;
import com.brayan.guardianfinanciero.dto.alert.FraudAlertSummaryResponse;
import com.brayan.guardianfinanciero.model.enums.AlertStatus;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.service.FraudAlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@Tag(name = "fraud-alert-controller", description = "Endpoints para la gestión de alertas antifraude por los analistas")
public class FraudAlertController {

    private final FraudAlertService fraudAlertService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar todas las alertas", description = "Devuelve todas las alertas de fraude registradas.")
    public ResponseEntity<List<FraudAlertResponse>> getAllAlerts() {
        return ResponseEntity.ok(fraudAlertService.getAllAlerts());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Detalle de alerta", description = "Busca una alerta específica por su ID.")
    public ResponseEntity<FraudAlertResponse> getAlertById(@PathVariable Long id) {
        return ResponseEntity.ok(fraudAlertService.getAlertById(id));
    }

    @GetMapping("/transaction/{transactionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Alerta por transacción", description = "Busca si existe una alerta asociada a una transacción.")
    public ResponseEntity<FraudAlertResponse> getAlertByTransactionId(@PathVariable Long transactionId) {
        return ResponseEntity.ok(fraudAlertService.getAlertByTransactionId(transactionId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar alertas por estado", description = "Filtra alertas por estado (PENDING, IN_REVIEW, CONFIRMED_FRAUD, etc.).")
    public ResponseEntity<List<FraudAlertResponse>> getAlertsByStatus(@PathVariable AlertStatus status) {
        return ResponseEntity.ok(fraudAlertService.getAlertsByStatus(status));
    }

    @GetMapping("/risk/{riskLevel}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar alertas por riesgo", description = "Filtra alertas por nivel de riesgo (HIGH, CRITICAL).")
    public ResponseEntity<List<FraudAlertResponse>> getAlertsByRiskLevel(@PathVariable RiskLevel riskLevel) {
        return ResponseEntity.ok(fraudAlertService.getAlertsByRiskLevel(riskLevel));
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar alertas por cliente", description = "Devuelve todas las alertas vinculadas a un cliente.")
    public ResponseEntity<List<FraudAlertResponse>> getAlertsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(fraudAlertService.getAlertsByCustomerId(customerId));
    }

    @PatchMapping("/{id}/take")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Tomar alerta", description = "Un analista toma la alerta y la cambia a IN_REVIEW, registrando quién la está revisando.")
    public ResponseEntity<FraudAlertResponse> takeAlertInReview(@PathVariable Long id) {
        return ResponseEntity.ok(fraudAlertService.takeAlertInReview(id));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Actualizar estado", description = "Cambia el estado de la alerta (ej. CONFIRMED_FRAUD o FALSE_POSITIVE) y actualiza la transacción asociada si es necesario.")
    public ResponseEntity<FraudAlertResponse> updateAlertStatus(
            @PathVariable Long id,
            @Valid @RequestBody FraudAlertStatusUpdateRequest request) {
        return ResponseEntity.ok(fraudAlertService.updateAlertStatus(id, request));
    }

    @PatchMapping("/{id}/comment")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Añadir comentario", description = "Agrega o sobrescribe el comentario de análisis en una alerta.")
    public ResponseEntity<FraudAlertResponse> addAnalystComment(
            @PathVariable Long id,
            @Valid @RequestBody FraudAlertCommentRequest request) {
        return ResponseEntity.ok(fraudAlertService.addAnalystComment(id, request));
    }

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Resumen de alertas", description = "Obtiene estadísticas consolidadas del módulo de alertas.")
    public ResponseEntity<FraudAlertSummaryResponse> getAlertSummary() {
        return ResponseEntity.ok(fraudAlertService.getAlertSummary());
    }
}
