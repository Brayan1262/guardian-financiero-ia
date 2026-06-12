package com.brayan.guardianfinanciero.service.risk;

import com.brayan.guardianfinanciero.model.FinancialTransaction;
import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionChannel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import com.brayan.guardianfinanciero.model.enums.TransactionType;
import com.brayan.guardianfinanciero.repository.FinancialTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskEngineService {

    private final FinancialTransactionRepository transactionRepository;

    public RiskEvaluationResult evaluateTransaction(FinancialTransaction transaction) {
        int riskScore = 0;
        List<String> triggeredRules = new ArrayList<>();
        StringBuilder explanation = new StringBuilder();

        BigDecimal amount = transaction.getAmount();

        // A) HIGH_AMOUNT
        if (amount != null && amount.compareTo(new BigDecimal("5000")) >= 0 && amount.compareTo(new BigDecimal("10000")) < 0) {
            riskScore += 30;
            triggeredRules.add("HIGH_AMOUNT");
            explanation.append("Monto elevado respecto a una operación regular. ");
        }

        // B) VERY_HIGH_AMOUNT
        if (amount != null && amount.compareTo(new BigDecimal("10000")) >= 0) {
            riskScore += 45;
            triggeredRules.add("VERY_HIGH_AMOUNT");
            explanation.append("Monto muy elevado que requiere revisión prioritaria. ");
        }

        // C) UNUSUAL_HOUR
        int hour = transaction.getTransactionDateTime().getHour();
        if (hour >= 0 && hour < 5) {
            riskScore += 20;
            triggeredRules.add("UNUSUAL_HOUR");
            explanation.append("Operación realizada en horario inusual. ");
        }

        // D) DIGITAL_CHANNEL
        if (transaction.getChannel() == TransactionChannel.WEB || transaction.getChannel() == TransactionChannel.MOBILE) {
            riskScore += 10;
            triggeredRules.add("DIGITAL_CHANNEL");
            explanation.append("Operación realizada por canal digital. ");
        }

        // E) DIFFERENT_LOCATION
        if (transaction.getOriginLocation() != null && transaction.getDestinationLocation() != null
                && !transaction.getOriginLocation().equalsIgnoreCase(transaction.getDestinationLocation())) {
            riskScore += 15;
            triggeredRules.add("DIFFERENT_LOCATION");
            explanation.append("Origen y destino de la operación son diferentes. ");
        }

        // F) CUSTOMER_UNDER_REVIEW
        if (transaction.getCustomer().getStatus() == CustomerStatus.UNDER_REVIEW) {
            riskScore += 25;
            triggeredRules.add("CUSTOMER_UNDER_REVIEW");
            explanation.append("Cliente se encuentra bajo revisión. ");
        }

        // G) BLOCKED_CUSTOMER
        if (transaction.getCustomer().getStatus() == CustomerStatus.BLOCKED) {
            riskScore += 60;
            triggeredRules.add("BLOCKED_CUSTOMER");
            explanation.append("Cliente bloqueado, operación altamente riesgosa. ");
        }

        // H) TRANSFER_OPERATION
        if (transaction.getTransactionType() == TransactionType.TRANSFER) {
            riskScore += 10;
            triggeredRules.add("TRANSFER_OPERATION");
            explanation.append("Las transferencias requieren mayor monitoreo. ");
        }

        // I) ATM_WITHDRAWAL_HIGH
        if (transaction.getTransactionType() == TransactionType.WITHDRAWAL
                && transaction.getChannel() == TransactionChannel.ATM
                && amount != null && amount.compareTo(new BigDecimal("1000")) >= 0) {
            riskScore += 25;
            triggeredRules.add("ATM_WITHDRAWAL_HIGH");
            explanation.append("Retiro elevado por cajero automático. ");
        }

        // J) MULTIPLE_RECENT_TRANSACTIONS
        LocalDateTime start = LocalDateTime.now().minusHours(24);
        Long recentTransactionsCount = transactionRepository.countByCustomerIdAndTransactionDateTimeBetween(
                transaction.getCustomer().getId(), start, LocalDateTime.now());

        if (recentTransactionsCount >= 3) {
            riskScore += 20;
            triggeredRules.add("MULTIPLE_RECENT_TRANSACTIONS");
            explanation.append("Cliente con múltiples operaciones recientes. ");
        }

        // Asignar RiskLevel según score
        RiskLevel riskLevel;
        if (riskScore <= 29) {
            riskLevel = RiskLevel.LOW;
        } else if (riskScore <= 59) {
            riskLevel = RiskLevel.MEDIUM;
        } else if (riskScore <= 84) {
            riskLevel = RiskLevel.HIGH;
        } else {
            riskLevel = RiskLevel.CRITICAL;
        }

        // Estado de la transacción según riesgo
        TransactionStatus status;
        String recommendedAction;

        if (transaction.getCustomer().getStatus() == CustomerStatus.BLOCKED) {
            status = TransactionStatus.REJECTED;
            recommendedAction = "Rechazar operación por cliente bloqueado.";
        } else {
            switch (riskLevel) {
                case CRITICAL:
                    status = TransactionStatus.UNDER_REVIEW;
                    recommendedAction = "Bloquear temporalmente la operación y escalar a revisión prioritaria.";
                    break;
                case HIGH:
                    status = TransactionStatus.UNDER_REVIEW;
                    recommendedAction = "Enviar operación a revisión manual por analista.";
                    break;
                case MEDIUM:
                    status = TransactionStatus.PENDING;
                    recommendedAction = "Operación pendiente de monitoreo.";
                    break;
                case LOW:
                default:
                    status = TransactionStatus.APPROVED;
                    recommendedAction = "Operación aprobada automáticamente.";
                    break;
            }
        }

        return RiskEvaluationResult.builder()
                .riskScore(riskScore)
                .riskLevel(riskLevel)
                .riskExplanation(explanation.toString().trim())
                .triggeredRules(triggeredRules)
                .recommendedAction(recommendedAction)
                .transactionStatus(status)
                .build();
    }
}
