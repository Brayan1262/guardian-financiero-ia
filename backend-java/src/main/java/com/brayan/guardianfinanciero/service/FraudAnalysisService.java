package com.brayan.guardianfinanciero.service;

import com.brayan.guardianfinanciero.dto.risk.RiskAnalysisResponse;
import com.brayan.guardianfinanciero.exception.ResourceNotFoundException;
import com.brayan.guardianfinanciero.model.FinancialTransaction;
import com.brayan.guardianfinanciero.model.FraudAlert;
import com.brayan.guardianfinanciero.model.enums.AlertStatus;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import com.brayan.guardianfinanciero.repository.FinancialTransactionRepository;
import com.brayan.guardianfinanciero.repository.FraudAlertRepository;
import com.brayan.guardianfinanciero.service.risk.RiskEngineService;
import com.brayan.guardianfinanciero.service.risk.RiskEvaluationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FraudAnalysisService {

    private final FinancialTransactionRepository transactionRepository;
    private final FraudAlertRepository fraudAlertRepository;
    private final RiskEngineService riskEngineService;

    @Transactional
    public RiskAnalysisResponse analyzeTransaction(Long transactionId) {
        FinancialTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con id: " + transactionId));

        return executeAnalysis(transaction);
    }

    @Transactional
    public RiskAnalysisResponse reanalyzeTransaction(Long transactionId) {
        FinancialTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con id: " + transactionId));

        return executeAnalysis(transaction);
    }

    @Transactional
    public List<RiskAnalysisResponse> analyzePendingTransactions() {
        List<FinancialTransaction> pendingTransactions = transactionRepository.findByStatus(TransactionStatus.PENDING);
        
        return pendingTransactions.stream()
                .map(this::executeAnalysis)
                .collect(Collectors.toList());
    }

    private RiskAnalysisResponse executeAnalysis(FinancialTransaction transaction) {
        RiskEvaluationResult result = riskEngineService.evaluateTransaction(transaction);

        // Update transaction
        transaction.setRiskScore(result.getRiskScore());
        transaction.setRiskLevel(result.getRiskLevel());
        transaction.setRiskExplanation(result.getRiskExplanation());
        transaction.setStatus(result.getTransactionStatus());
        transaction = transactionRepository.save(transaction);

        // Create alert if HIGH or CRITICAL
        boolean alertGenerated = false;
        Long alertId = null;
        if (result.getRiskLevel() == RiskLevel.HIGH || result.getRiskLevel() == RiskLevel.CRITICAL) {
            Optional<FraudAlert> existingAlert = fraudAlertRepository.findByTransactionId(transaction.getId());
            if (existingAlert.isEmpty()) {
                FraudAlert alert = FraudAlert.builder()
                        .transaction(transaction)
                        .customer(transaction.getCustomer())
                        .riskLevel(result.getRiskLevel())
                        .riskScore(result.getRiskScore())
                        .status(AlertStatus.PENDING)
                        .reason(result.getRiskExplanation())
                        .build();
                alert = fraudAlertRepository.save(alert);
                alertGenerated = true;
                alertId = alert.getId();
            } else {
                alertGenerated = true;
                alertId = existingAlert.get().getId();
            }
        }

        return RiskAnalysisResponse.builder()
                .transactionId(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .customerFullName(transaction.getCustomer().getFullName())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .channel(transaction.getChannel())
                .riskScore(result.getRiskScore())
                .riskLevel(result.getRiskLevel())
                .riskExplanation(result.getRiskExplanation())
                .triggeredRules(result.getTriggeredRules())
                .recommendedAction(result.getRecommendedAction())
                .transactionStatus(result.getTransactionStatus())
                .analyzedAt(LocalDateTime.now())
                .alertGenerated(alertGenerated)
                .alertId(alertId)
                .build();
    }
}
