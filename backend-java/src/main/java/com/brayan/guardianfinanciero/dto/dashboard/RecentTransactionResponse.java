package com.brayan.guardianfinanciero.dto.dashboard;

import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionChannel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import com.brayan.guardianfinanciero.model.enums.TransactionType;
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
public class RecentTransactionResponse {
    private Long id;
    private String customerFullName;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionChannel channel;
    private TransactionStatus status;
    private RiskLevel riskLevel;
    private Integer riskScore;
    private LocalDateTime transactionDateTime;
}
