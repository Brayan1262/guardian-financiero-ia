package com.brayan.guardianfinanciero.controller;

import com.brayan.guardianfinanciero.dto.risk.RiskAnalysisResponse;
import com.brayan.guardianfinanciero.dto.risk.RiskRuleResponse;
import com.brayan.guardianfinanciero.service.FraudAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/risk-analysis")
@RequiredArgsConstructor
@Tag(name = "risk-analysis-controller", description = "Endpoints para el motor de reglas y análisis antifraude")
public class RiskAnalysisController {

    private final FraudAnalysisService fraudAnalysisService;

    @PostMapping("/transactions/{transactionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Analizar transacción", description = "Ejecuta el motor de reglas interno sobre una transacción específica para calcular su riesgo y actualizar su estado. Requiere ADMIN o ANALYST.")
    public ResponseEntity<RiskAnalysisResponse> analyzeTransaction(@PathVariable Long transactionId) {
        return ResponseEntity.ok(fraudAnalysisService.analyzeTransaction(transactionId));
    }

    @PostMapping("/transactions/{transactionId}/reanalyze")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Reanalizar transacción", description = "Vuelve a ejecutar el motor de reglas sobre una transacción. Útil si se agregaron nuevas reglas.")
    public ResponseEntity<RiskAnalysisResponse> reanalyzeTransaction(@PathVariable Long transactionId) {
        return ResponseEntity.ok(fraudAnalysisService.reanalyzeTransaction(transactionId));
    }

    @PostMapping("/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Analizar pendientes", description = "Ejecuta el motor de reglas masivamente sobre todas las transacciones en estado PENDING.")
    public ResponseEntity<List<RiskAnalysisResponse>> analyzePendingTransactions() {
        return ResponseEntity.ok(fraudAnalysisService.analyzePendingTransactions());
    }

    @GetMapping("/rules")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar reglas activas", description = "Muestra la lista de las reglas de riesgo documentadas en el motor actual.")
    public ResponseEntity<List<RiskRuleResponse>> getActiveRules() {
        List<RiskRuleResponse> rules = new ArrayList<>();
        rules.add(new RiskRuleResponse(1L, "HIGH_AMOUNT", "Monto Alto", "Monto >= 5000", 30, true, LocalDateTime.now(), LocalDateTime.now()));
        rules.add(new RiskRuleResponse(2L, "VERY_HIGH_AMOUNT", "Monto Muy Alto", "Monto >= 10000", 45, true, LocalDateTime.now(), LocalDateTime.now()));
        rules.add(new RiskRuleResponse(3L, "UNUSUAL_HOUR", "Hora inusual", "Entre 00:00 y 05:00", 20, true, LocalDateTime.now(), LocalDateTime.now()));
        rules.add(new RiskRuleResponse(4L, "DIGITAL_CHANNEL", "Canal Digital", "WEB o MOBILE", 10, true, LocalDateTime.now(), LocalDateTime.now()));
        rules.add(new RiskRuleResponse(5L, "DIFFERENT_LOCATION", "Ubicación diferente", "Origen y Destino distintos", 15, true, LocalDateTime.now(), LocalDateTime.now()));
        rules.add(new RiskRuleResponse(6L, "CUSTOMER_UNDER_REVIEW", "Cliente en revisión", "Cliente tiene estado UNDER_REVIEW", 25, true, LocalDateTime.now(), LocalDateTime.now()));
        rules.add(new RiskRuleResponse(7L, "BLOCKED_CUSTOMER", "Cliente bloqueado", "Cliente tiene estado BLOCKED", 60, true, LocalDateTime.now(), LocalDateTime.now()));
        rules.add(new RiskRuleResponse(8L, "TRANSFER_OPERATION", "Operación de Transferencia", "Tipo es TRANSFER", 10, true, LocalDateTime.now(), LocalDateTime.now()));
        rules.add(new RiskRuleResponse(9L, "ATM_WITHDRAWAL_HIGH", "Retiro ATM Alto", "Retiro por cajero >= 1000", 25, true, LocalDateTime.now(), LocalDateTime.now()));
        rules.add(new RiskRuleResponse(10L, "MULTIPLE_RECENT_TRANSACTIONS", "Transacciones recientes", "3 o más transacciones en 24h", 20, true, LocalDateTime.now(), LocalDateTime.now()));
        
        return ResponseEntity.ok(rules);
    }
}
