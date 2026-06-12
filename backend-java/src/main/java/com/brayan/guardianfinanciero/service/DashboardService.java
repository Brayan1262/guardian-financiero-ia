package com.brayan.guardianfinanciero.service;

import com.brayan.guardianfinanciero.dto.dashboard.*;
import com.brayan.guardianfinanciero.model.enums.*;
import com.brayan.guardianfinanciero.repository.CustomerRepository;
import com.brayan.guardianfinanciero.repository.FinancialTransactionRepository;
import com.brayan.guardianfinanciero.repository.FraudAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final FinancialTransactionRepository transactionRepository;
    private final FraudAlertRepository fraudAlertRepository;

    public DashboardSummaryResponse getSummary() {
        return DashboardSummaryResponse.builder()
                .totalCustomers(customerRepository.count())
                .activeCustomers(customerRepository.countByStatus(CustomerStatus.ACTIVE))
                .blockedCustomers(customerRepository.countByStatus(CustomerStatus.BLOCKED))
                .underReviewCustomers(customerRepository.countByStatus(CustomerStatus.UNDER_REVIEW))
                
                .totalTransactions(transactionRepository.count())
                .totalTransactionAmount(transactionRepository.getTotalTransactionAmount())
                .averageTransactionAmount(transactionRepository.getAverageTransactionAmount())
                .pendingTransactions(transactionRepository.countByStatus(TransactionStatus.PENDING))
                .approvedTransactions(transactionRepository.countByStatus(TransactionStatus.APPROVED))
                .rejectedTransactions(transactionRepository.countByStatus(TransactionStatus.REJECTED))
                .underReviewTransactions(transactionRepository.countByStatus(TransactionStatus.UNDER_REVIEW))
                
                .totalAlerts(fraudAlertRepository.count())
                .pendingAlerts(fraudAlertRepository.countByStatus(AlertStatus.PENDING))
                .inReviewAlerts(fraudAlertRepository.countByStatus(AlertStatus.IN_REVIEW))
                .confirmedFraudAlerts(fraudAlertRepository.countByStatus(AlertStatus.CONFIRMED_FRAUD))
                .falsePositiveAlerts(fraudAlertRepository.countByStatus(AlertStatus.FALSE_POSITIVE))
                .resolvedAlerts(fraudAlertRepository.countByStatus(AlertStatus.RESOLVED))
                
                .highRiskTransactions(transactionRepository.countByRiskLevel(RiskLevel.HIGH))
                .criticalRiskTransactions(transactionRepository.countByRiskLevel(RiskLevel.CRITICAL))
                .highRiskAlerts(fraudAlertRepository.countByRiskLevel(RiskLevel.HIGH))
                .criticalRiskAlerts(fraudAlertRepository.countByRiskLevel(RiskLevel.CRITICAL))
                
                .generatedAt(LocalDateTime.now())
                .build();
    }

    public List<MetricCardResponse> getMetricCards() {
        DashboardSummaryResponse summary = getSummary();
        List<MetricCardResponse> cards = new ArrayList<>();
        
        cards.add(new MetricCardResponse("Total Transacciones", String.valueOf(summary.getTotalTransactions()), "Cantidad de operaciones registradas", "TRANSACTIONS"));
        cards.add(new MetricCardResponse("Total Clientes", String.valueOf(summary.getTotalCustomers()), "Usuarios registrados en la plataforma", "CUSTOMERS"));
        cards.add(new MetricCardResponse("Alertas Activas", String.valueOf(summary.getPendingAlerts() + summary.getInReviewAlerts()), "Alertas pendientes o en revisión", "ALERTS"));
        cards.add(new MetricCardResponse("Monto Procesado", "$" + summary.getTotalTransactionAmount(), "Suma total de todas las transacciones", "AMOUNT"));
        
        return cards;
    }

    public DashboardChartsResponse getCharts() {
        DashboardChartsResponse charts = new DashboardChartsResponse();
        
        List<ChartDataResponse> txByStatus = new ArrayList<>();
        for (TransactionStatus status : TransactionStatus.values()) {
            txByStatus.add(new ChartDataResponse(status.name(), transactionRepository.countByStatus(status)));
        }
        charts.setTransactionsByStatus(txByStatus);
        
        List<ChartDataResponse> txByRisk = new ArrayList<>();
        for (RiskLevel risk : RiskLevel.values()) {
            txByRisk.add(new ChartDataResponse(risk.name(), transactionRepository.countByRiskLevel(risk)));
        }
        charts.setTransactionsByRiskLevel(txByRisk);
        
        List<ChartDataResponse> txByChannel = new ArrayList<>();
        for (TransactionChannel channel : TransactionChannel.values()) {
            txByChannel.add(new ChartDataResponse(channel.name(), transactionRepository.countByChannel(channel)));
        }
        charts.setTransactionsByChannel(txByChannel);
        
        List<ChartDataResponse> alertsByStatus = new ArrayList<>();
        for (AlertStatus status : AlertStatus.values()) {
            alertsByStatus.add(new ChartDataResponse(status.name(), fraudAlertRepository.countByStatus(status)));
        }
        charts.setAlertsByStatus(alertsByStatus);
        
        List<ChartDataResponse> alertsByRisk = new ArrayList<>();
        for (RiskLevel risk : RiskLevel.values()) {
            alertsByRisk.add(new ChartDataResponse(risk.name(), fraudAlertRepository.countByRiskLevel(risk)));
        }
        charts.setAlertsByRiskLevel(alertsByRisk);
        
        List<ChartDataResponse> customersByStatus = new ArrayList<>();
        for (CustomerStatus status : CustomerStatus.values()) {
            customersByStatus.add(new ChartDataResponse(status.name(), customerRepository.countByStatus(status)));
        }
        charts.setCustomersByStatus(customersByStatus);
        
        return charts;
    }

    public List<RecentTransactionResponse> getRecentTransactions() {
        return transactionRepository.findTop5ByOrderByCreatedAtDesc().stream().map(tx -> 
            RecentTransactionResponse.builder()
                .id(tx.getId())
                .customerFullName(tx.getCustomer() != null ? tx.getCustomer().getFullName() : "N/A")
                .amount(tx.getAmount())
                .transactionType(tx.getTransactionType())
                .channel(tx.getChannel())
                .status(tx.getStatus())
                .riskLevel(tx.getRiskLevel())
                .riskScore(tx.getRiskScore())
                .transactionDateTime(tx.getTransactionDateTime())
                .build()
        ).collect(Collectors.toList());
    }

    public List<RecentAlertResponse> getRecentAlerts() {
        return fraudAlertRepository.findTop5ByOrderByCreatedAtDesc().stream().map(alert -> 
            RecentAlertResponse.builder()
                .id(alert.getId())
                .transactionId(alert.getTransaction() != null ? alert.getTransaction().getId() : null)
                .customerFullName(alert.getCustomer() != null ? alert.getCustomer().getFullName() : "N/A")
                .riskLevel(alert.getRiskLevel())
                .riskScore(alert.getRiskScore())
                .status(alert.getStatus())
                .createdAt(alert.getCreatedAt())
                .build()
        ).collect(Collectors.toList());
    }

    public DashboardOverviewResponse getOverview() {
        return DashboardOverviewResponse.builder()
                .summary(getSummary())
                .metricCards(getMetricCards())
                .charts(getCharts())
                .recentTransactions(getRecentTransactions())
                .recentAlerts(getRecentAlerts())
                .build();
    }
}
