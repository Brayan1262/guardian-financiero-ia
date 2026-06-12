package com.brayan.guardianfinanciero.controller;

import com.brayan.guardianfinanciero.dto.dashboard.*;
import com.brayan.guardianfinanciero.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "dashboard-controller", description = "Endpoints para métricas y dashboard de la plataforma")
@PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'AUDITOR')")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    @Operation(summary = "Resumen general de métricas", description = "Obtiene los contadores y sumatorias totales del sistema.")
    public ResponseEntity<DashboardSummaryResponse> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/cards")
    @Operation(summary = "Métricas para Cards", description = "Obtiene una lista de las métricas clave para mostrar en las tarjetas principales del dashboard.")
    public ResponseEntity<List<MetricCardResponse>> getMetricCards() {
        return ResponseEntity.ok(dashboardService.getMetricCards());
    }

    @GetMapping("/charts")
    @Operation(summary = "Datos para Gráficos", description = "Devuelve información agrupada lista para alimentar gráficos (ej. Pie, Bar).")
    public ResponseEntity<DashboardChartsResponse> getCharts() {
        return ResponseEntity.ok(dashboardService.getCharts());
    }

    @GetMapping("/recent-transactions")
    @Operation(summary = "Últimas transacciones", description = "Devuelve la lista de las últimas 5 transacciones registradas.")
    public ResponseEntity<List<RecentTransactionResponse>> getRecentTransactions() {
        return ResponseEntity.ok(dashboardService.getRecentTransactions());
    }

    @GetMapping("/recent-alerts")
    @Operation(summary = "Últimas alertas", description = "Devuelve la lista de las últimas 5 alertas de fraude generadas.")
    public ResponseEntity<List<RecentAlertResponse>> getRecentAlerts() {
        return ResponseEntity.ok(dashboardService.getRecentAlerts());
    }

    @GetMapping("/overview")
    @Operation(summary = "Dashboard completo", description = "Devuelve toda la información del dashboard en una sola llamada (ideal para carga inicial de frontend).")
    public ResponseEntity<DashboardOverviewResponse> getOverview() {
        return ResponseEntity.ok(dashboardService.getOverview());
    }
}
