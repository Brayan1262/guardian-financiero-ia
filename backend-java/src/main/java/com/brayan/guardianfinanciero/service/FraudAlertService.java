package com.brayan.guardianfinanciero.service;

import com.brayan.guardianfinanciero.dto.alert.FraudAlertCommentRequest;
import com.brayan.guardianfinanciero.dto.alert.FraudAlertResponse;
import com.brayan.guardianfinanciero.dto.alert.FraudAlertStatusUpdateRequest;
import com.brayan.guardianfinanciero.dto.alert.FraudAlertSummaryResponse;
import com.brayan.guardianfinanciero.exception.ResourceNotFoundException;
import com.brayan.guardianfinanciero.mapper.FraudAlertMapper;
import com.brayan.guardianfinanciero.model.FinancialTransaction;
import com.brayan.guardianfinanciero.model.FraudAlert;
import com.brayan.guardianfinanciero.model.enums.AlertStatus;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import com.brayan.guardianfinanciero.repository.AppUserRepository;
import com.brayan.guardianfinanciero.repository.FinancialTransactionRepository;
import com.brayan.guardianfinanciero.repository.FraudAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FraudAlertService {

    private final FraudAlertRepository fraudAlertRepository;
    private final FinancialTransactionRepository transactionRepository;
    private final AppUserRepository appUserRepository;
    private final FraudAlertMapper fraudAlertMapper;

    public List<FraudAlertResponse> getAllAlerts() {
        return fraudAlertRepository.findAll().stream()
                .map(fraudAlertMapper::toResponse)
                .collect(Collectors.toList());
    }

    public FraudAlertResponse getAlertById(Long id) {
        FraudAlert alert = fraudAlertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con id: " + id));
        return fraudAlertMapper.toResponse(alert);
    }

    public FraudAlertResponse getAlertByTransactionId(Long transactionId) {
        FraudAlert alert = fraudAlertRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada para la transacción con id: " + transactionId));
        return fraudAlertMapper.toResponse(alert);
    }

    public List<FraudAlertResponse> getAlertsByStatus(AlertStatus status) {
        return fraudAlertRepository.findByStatus(status).stream()
                .map(fraudAlertMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<FraudAlertResponse> getAlertsByRiskLevel(RiskLevel riskLevel) {
        return fraudAlertRepository.findByRiskLevel(riskLevel).stream()
                .map(fraudAlertMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<FraudAlertResponse> getAlertsByCustomerId(Long customerId) {
        return fraudAlertRepository.findByCustomerId(customerId).stream()
                .map(fraudAlertMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public FraudAlertResponse takeAlertInReview(Long alertId) {
        FraudAlert alert = fraudAlertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con id: " + alertId));

        alert.setStatus(AlertStatus.IN_REVIEW);
        alert.setReviewedAt(LocalDateTime.now());
        assignCurrentUserToAlert(alert);

        alert = fraudAlertRepository.save(alert);
        return fraudAlertMapper.toResponse(alert);
    }

    @Transactional
    public FraudAlertResponse updateAlertStatus(Long alertId, FraudAlertStatusUpdateRequest request) {
        FraudAlert alert = fraudAlertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con id: " + alertId));

        alert.setStatus(request.getStatus());
        alert.setReviewedAt(LocalDateTime.now());
        assignCurrentUserToAlert(alert);

        if (request.getAnalystComment() != null && !request.getAnalystComment().trim().isEmpty()) {
            alert.setAnalystComment(request.getAnalystComment());
        }

        FinancialTransaction transaction = alert.getTransaction();

        if (request.getStatus() == AlertStatus.CONFIRMED_FRAUD) {
            transaction.setStatus(TransactionStatus.REJECTED);
            transactionRepository.save(transaction);
        } else if (request.getStatus() == AlertStatus.FALSE_POSITIVE) {
            transaction.setStatus(TransactionStatus.APPROVED);
            transactionRepository.save(transaction);
        } else if (request.getStatus() == AlertStatus.RESOLVED) {
            if (transaction.getStatus() == TransactionStatus.UNDER_REVIEW) {
                transaction.setStatus(TransactionStatus.APPROVED);
                transactionRepository.save(transaction);
            }
        }

        alert = fraudAlertRepository.save(alert);
        return fraudAlertMapper.toResponse(alert);
    }

    @Transactional
    public FraudAlertResponse addAnalystComment(Long alertId, FraudAlertCommentRequest request) {
        FraudAlert alert = fraudAlertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con id: " + alertId));

        alert.setAnalystComment(request.getAnalystComment());
        alert.setReviewedAt(LocalDateTime.now());
        assignCurrentUserToAlert(alert);

        alert = fraudAlertRepository.save(alert);
        return fraudAlertMapper.toResponse(alert);
    }

    public FraudAlertSummaryResponse getAlertSummary() {
        return FraudAlertSummaryResponse.builder()
                .totalAlerts(fraudAlertRepository.count())
                .pendingAlerts(fraudAlertRepository.countByStatus(AlertStatus.PENDING))
                .inReviewAlerts(fraudAlertRepository.countByStatus(AlertStatus.IN_REVIEW))
                .confirmedFraudAlerts(fraudAlertRepository.countByStatus(AlertStatus.CONFIRMED_FRAUD))
                .falsePositiveAlerts(fraudAlertRepository.countByStatus(AlertStatus.FALSE_POSITIVE))
                .resolvedAlerts(fraudAlertRepository.countByStatus(AlertStatus.RESOLVED))
                .highRiskAlerts(fraudAlertRepository.countByRiskLevel(RiskLevel.HIGH))
                .criticalRiskAlerts(fraudAlertRepository.countByRiskLevel(RiskLevel.CRITICAL))
                .build();
    }

    private void assignCurrentUserToAlert(FraudAlert alert) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            String email = authentication.getName();
            appUserRepository.findByEmail(email).ifPresent(alert::setReviewedBy);
        }
    }
}
