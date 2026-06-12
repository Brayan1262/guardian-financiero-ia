package com.brayan.guardianfinanciero.service.risk;

import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluationResult {
    private Integer riskScore;
    private RiskLevel riskLevel;
    private String riskExplanation;
    private List<String> triggeredRules;
    private String recommendedAction;
    private TransactionStatus transactionStatus;
}
