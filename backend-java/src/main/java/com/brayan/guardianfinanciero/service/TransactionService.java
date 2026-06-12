package com.brayan.guardianfinanciero.service;

import com.brayan.guardianfinanciero.dto.transaction.TransactionCreateRequest;
import com.brayan.guardianfinanciero.dto.transaction.TransactionResponse;
import com.brayan.guardianfinanciero.dto.transaction.TransactionStatusUpdateRequest;
import com.brayan.guardianfinanciero.dto.transaction.TransactionSummaryResponse;
import com.brayan.guardianfinanciero.exception.BusinessException;
import com.brayan.guardianfinanciero.exception.ResourceNotFoundException;
import com.brayan.guardianfinanciero.mapper.TransactionMapper;
import com.brayan.guardianfinanciero.model.Customer;
import com.brayan.guardianfinanciero.model.FinancialTransaction;
import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionChannel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import com.brayan.guardianfinanciero.model.enums.TransactionType;
import com.brayan.guardianfinanciero.repository.CustomerRepository;
import com.brayan.guardianfinanciero.repository.FinancialTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final FinancialTransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final TransactionMapper transactionMapper;

    public TransactionResponse createTransaction(TransactionCreateRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + request.getCustomerId()));

        if (customer.getStatus() == CustomerStatus.BLOCKED) {
            throw new BusinessException("No se pueden registrar transacciones para un cliente BLOQUEADO.");
        }

        FinancialTransaction transaction = transactionMapper.toEntity(request, customer);
        
        transaction.setRiskScore(0);
        transaction.setRiskLevel(RiskLevel.LOW);
        
        if (customer.getStatus() == CustomerStatus.UNDER_REVIEW) {
            transaction.setStatus(TransactionStatus.UNDER_REVIEW);
        } else {
            transaction.setStatus(TransactionStatus.PENDING);
        }

        transaction = transactionRepository.save(transaction);
        return transactionMapper.toResponse(transaction);
    }

    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponse getTransactionById(Long id) {
        FinancialTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con id: " + id));
        return transactionMapper.toResponse(transaction);
    }

    public List<TransactionResponse> getTransactionsByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + customerId);
        }
        return transactionRepository.findByCustomerId(customerId).stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByStatus(status).stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionsByRiskLevel(RiskLevel riskLevel) {
        return transactionRepository.findByRiskLevel(riskLevel).stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionsByType(TransactionType transactionType) {
        return transactionRepository.findByTransactionType(transactionType).stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionsByChannel(TransactionChannel channel) {
        return transactionRepository.findByChannel(channel).stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByTransactionDateTimeBetween(start, end).stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponse updateTransactionStatus(Long id, TransactionStatusUpdateRequest request) {
        FinancialTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con id: " + id));

        transaction.setStatus(request.getStatus());
        transaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(transaction);
    }

    public TransactionSummaryResponse getTransactionSummary() {
        Long total = transactionRepository.count();
        Long pending = transactionRepository.countByStatus(TransactionStatus.PENDING);
        Long approved = transactionRepository.countByStatus(TransactionStatus.APPROVED);
        Long rejected = transactionRepository.countByStatus(TransactionStatus.REJECTED);
        Long underReview = transactionRepository.countByStatus(TransactionStatus.UNDER_REVIEW);
        
        BigDecimal totalAmount = transactionRepository.sumTotalAmount();
        Double avgAmountDouble = transactionRepository.averageAmount();
        BigDecimal averageAmount = avgAmountDouble != null ? BigDecimal.valueOf(avgAmountDouble) : BigDecimal.ZERO;

        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        return TransactionSummaryResponse.builder()
                .totalTransactions(total)
                .pendingTransactions(pending)
                .approvedTransactions(approved)
                .rejectedTransactions(rejected)
                .underReviewTransactions(underReview)
                .totalAmount(totalAmount)
                .averageAmount(averageAmount)
                .build();
    }
}
