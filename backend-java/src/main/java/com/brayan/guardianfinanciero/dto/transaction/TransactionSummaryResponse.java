package com.brayan.guardianfinanciero.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryResponse {
    private Long totalTransactions;
    private Long pendingTransactions;
    private Long approvedTransactions;
    private Long rejectedTransactions;
    private Long underReviewTransactions;
    private BigDecimal totalAmount;
    private BigDecimal averageAmount;
}
