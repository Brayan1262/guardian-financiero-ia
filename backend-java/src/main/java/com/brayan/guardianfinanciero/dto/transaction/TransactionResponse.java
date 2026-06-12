package com.brayan.guardianfinanciero.dto.transaction;

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
public class TransactionResponse {
    private Long id;
    private Long customerId;
    private String customerDocumentNumber;
    private String customerFullName;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionChannel channel;
    private TransactionStatus status;
    private String originLocation;
    private String destinationLocation;
    private String deviceId;
    private LocalDateTime transactionDateTime;
    private Integer riskScore;
    private RiskLevel riskLevel;
    private String riskExplanation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
