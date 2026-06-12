package com.brayan.guardianfinanciero.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {
    private Long totalCustomers;
    private Long activeCustomers;
    private Long blockedCustomers;
    private Long underReviewCustomers;
    
    private Long totalTransactions;
    private BigDecimal totalTransactionAmount;
    private BigDecimal averageTransactionAmount;
    private Long pendingTransactions;
    private Long approvedTransactions;
    private Long rejectedTransactions;
    private Long underReviewTransactions;
    
    private Long totalAlerts;
    private Long pendingAlerts;
    private Long inReviewAlerts;
    private Long confirmedFraudAlerts;
    private Long falsePositiveAlerts;
    private Long resolvedAlerts;
    
    private Long highRiskTransactions;
    private Long criticalRiskTransactions;
    private Long highRiskAlerts;
    private Long criticalRiskAlerts;
    
    private LocalDateTime generatedAt;
}
