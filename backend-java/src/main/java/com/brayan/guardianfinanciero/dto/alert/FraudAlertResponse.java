package com.brayan.guardianfinanciero.dto.alert;

import com.brayan.guardianfinanciero.model.enums.AlertStatus;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionChannel;
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
public class FraudAlertResponse {
    private Long id;
    private Long transactionId;
    private Long customerId;
    private String customerDocumentNumber;
    private String customerFullName;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionChannel channel;
    private RiskLevel riskLevel;
    private Integer riskScore;
    private AlertStatus status;
    private String reason;
    private String analystComment;
    private Long reviewedById;
    private String reviewedByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime reviewedAt;
}
