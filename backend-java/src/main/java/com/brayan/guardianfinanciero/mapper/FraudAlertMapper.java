package com.brayan.guardianfinanciero.mapper;

import com.brayan.guardianfinanciero.dto.alert.FraudAlertResponse;
import com.brayan.guardianfinanciero.model.FraudAlert;
import org.springframework.stereotype.Component;

@Component
public class FraudAlertMapper {

    public FraudAlertResponse toResponse(FraudAlert alert) {
        if (alert == null) {
            return null;
        }

        return FraudAlertResponse.builder()
                .id(alert.getId())
                .transactionId(alert.getTransaction() != null ? alert.getTransaction().getId() : null)
                .customerId(alert.getCustomer() != null ? alert.getCustomer().getId() : null)
                .customerDocumentNumber(alert.getCustomer() != null ? alert.getCustomer().getDocumentNumber() : null)
                .customerFullName(alert.getCustomer() != null ? alert.getCustomer().getFullName() : null)
                .amount(alert.getTransaction() != null ? alert.getTransaction().getAmount() : null)
                .transactionType(alert.getTransaction() != null ? alert.getTransaction().getTransactionType() : null)
                .channel(alert.getTransaction() != null ? alert.getTransaction().getChannel() : null)
                .riskLevel(alert.getRiskLevel())
                .riskScore(alert.getRiskScore())
                .status(alert.getStatus())
                .reason(alert.getReason())
                .analystComment(alert.getAnalystComment())
                .reviewedById(alert.getReviewedBy() != null ? alert.getReviewedBy().getId() : null)
                .reviewedByName(alert.getReviewedBy() != null ? alert.getReviewedBy().getFullName() : null)
                .createdAt(alert.getCreatedAt())
                .updatedAt(alert.getUpdatedAt())
                .reviewedAt(alert.getReviewedAt())
                .build();
    }
}
