package com.brayan.guardianfinanciero.mapper;

import com.brayan.guardianfinanciero.dto.transaction.TransactionCreateRequest;
import com.brayan.guardianfinanciero.dto.transaction.TransactionResponse;
import com.brayan.guardianfinanciero.model.Customer;
import com.brayan.guardianfinanciero.model.FinancialTransaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public FinancialTransaction toEntity(TransactionCreateRequest request, Customer customer) {
        if (request == null) {
            return null;
        }

        return FinancialTransaction.builder()
                .customer(customer)
                .amount(request.getAmount())
                .transactionType(request.getTransactionType())
                .channel(request.getChannel())
                .originLocation(request.getOriginLocation())
                .destinationLocation(request.getDestinationLocation())
                .deviceId(request.getDeviceId())
                .transactionDateTime(request.getTransactionDateTime())
                .build();
    }

    public TransactionResponse toResponse(FinancialTransaction transaction) {
        if (transaction == null) {
            return null;
        }

        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer() != null ? transaction.getCustomer().getId() : null)
                .customerDocumentNumber(transaction.getCustomer() != null ? transaction.getCustomer().getDocumentNumber() : null)
                .customerFullName(transaction.getCustomer() != null ? transaction.getCustomer().getFullName() : null)
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .channel(transaction.getChannel())
                .status(transaction.getStatus())
                .originLocation(transaction.getOriginLocation())
                .destinationLocation(transaction.getDestinationLocation())
                .deviceId(transaction.getDeviceId())
                .transactionDateTime(transaction.getTransactionDateTime())
                .riskScore(transaction.getRiskScore())
                .riskLevel(transaction.getRiskLevel())
                .riskExplanation(transaction.getRiskExplanation())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}
