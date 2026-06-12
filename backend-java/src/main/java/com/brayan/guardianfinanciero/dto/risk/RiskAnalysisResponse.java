package com.brayan.guardianfinanciero.dto.risk;

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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskAnalysisResponse {
    private Long transactionId;
    private Long customerId;
    private String customerFullName;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionChannel channel;
    private Integer riskScore;
    private RiskLevel riskLevel;
    private String riskExplanation;
    private List<String> triggeredRules;
    private String recommendedAction;
    private TransactionStatus transactionStatus;
    private LocalDateTime analyzedAt;
}
