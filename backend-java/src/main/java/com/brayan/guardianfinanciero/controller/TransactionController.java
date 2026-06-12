package com.brayan.guardianfinanciero.controller;

import com.brayan.guardianfinanciero.dto.transaction.TransactionCreateRequest;
import com.brayan.guardianfinanciero.dto.transaction.TransactionResponse;
import com.brayan.guardianfinanciero.dto.transaction.TransactionStatusUpdateRequest;
import com.brayan.guardianfinanciero.dto.transaction.TransactionSummaryResponse;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionChannel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import com.brayan.guardianfinanciero.model.enums.TransactionType;
import com.brayan.guardianfinanciero.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "transaction-controller", description = "Endpoints para la gestión de transacciones financieras")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Crear transacción", description = "Registra una nueva transacción financiera. Requiere rol ADMIN o ANALYST.")
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionCreateRequest request) {
        return new ResponseEntity<>(transactionService.createTransaction(request), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar todas las transacciones", description = "Devuelve todas las transacciones registradas.")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Buscar transacción por ID", description = "Busca una transacción específica usando su ID interno.")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar transacciones por cliente", description = "Devuelve todas las transacciones asociadas a un cliente.")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(transactionService.getTransactionsByCustomerId(customerId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar transacciones por estado", description = "Filtra transacciones por estado (ej. PENDING, APPROVED).")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByStatus(@PathVariable TransactionStatus status) {
        return ResponseEntity.ok(transactionService.getTransactionsByStatus(status));
    }

    @GetMapping("/risk/{riskLevel}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar transacciones por nivel de riesgo", description = "Filtra transacciones por nivel de riesgo (LOW, MEDIUM, HIGH, CRITICAL).")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByRiskLevel(@PathVariable RiskLevel riskLevel) {
        return ResponseEntity.ok(transactionService.getTransactionsByRiskLevel(riskLevel));
    }

    @GetMapping("/type/{transactionType}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar transacciones por tipo", description = "Filtra transacciones por tipo (TRANSFER, PAYMENT, etc.).")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByType(@PathVariable TransactionType transactionType) {
        return ResponseEntity.ok(transactionService.getTransactionsByType(transactionType));
    }

    @GetMapping("/channel/{channel}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar transacciones por canal", description = "Filtra transacciones por el canal utilizado (WEB, MOBILE, etc.).")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByChannel(@PathVariable TransactionChannel channel) {
        return ResponseEntity.ok(transactionService.getTransactionsByChannel(channel));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Listar transacciones por rango de fechas", description = "Devuelve transacciones en un rango de fechas especificado.")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(transactionService.getTransactionsByDateRange(start, end));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    @Operation(summary = "Actualizar estado de transacción", description = "Cambia manualmente el estado de una transacción. Requiere ADMIN o ANALYST.")
    public ResponseEntity<TransactionResponse> updateTransactionStatus(
            @PathVariable Long id, 
            @Valid @RequestBody TransactionStatusUpdateRequest request) {
        return ResponseEntity.ok(transactionService.updateTransactionStatus(id, request));
    }

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
    @Operation(summary = "Resumen de transacciones", description = "Obtiene estadísticas generales sobre las transacciones del sistema.")
    public ResponseEntity<TransactionSummaryResponse> getTransactionSummary() {
        return ResponseEntity.ok(transactionService.getTransactionSummary());
    }
}
