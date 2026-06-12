package com.brayan.guardianfinanciero.service;

import com.brayan.guardianfinanciero.dto.alert.FraudAlertResponse;
import com.brayan.guardianfinanciero.dto.alert.FraudAlertStatusUpdateRequest;
import com.brayan.guardianfinanciero.mapper.FraudAlertMapper;
import com.brayan.guardianfinanciero.model.FinancialTransaction;
import com.brayan.guardianfinanciero.model.FraudAlert;
import com.brayan.guardianfinanciero.model.enums.AlertStatus;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import com.brayan.guardianfinanciero.repository.AppUserRepository;
import com.brayan.guardianfinanciero.repository.FinancialTransactionRepository;
import com.brayan.guardianfinanciero.repository.FraudAlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FraudAlertServiceTest {

    @Mock
    private FraudAlertRepository fraudAlertRepository;

    @Mock
    private FinancialTransactionRepository transactionRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private FraudAlertMapper fraudAlertMapper;

    @InjectMocks
    private FraudAlertService fraudAlertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void takeAlertInReview_Success() {
        FraudAlert alert = new FraudAlert();
        alert.setId(1L);
        alert.setStatus(AlertStatus.PENDING);

        FraudAlert savedAlert = new FraudAlert();
        savedAlert.setId(1L);
        savedAlert.setStatus(AlertStatus.IN_REVIEW);

        FraudAlertResponse responseMock = new FraudAlertResponse();
        responseMock.setId(1L);
        responseMock.setStatus(AlertStatus.IN_REVIEW);

        when(fraudAlertRepository.findById(1L)).thenReturn(Optional.of(alert));
        when(fraudAlertRepository.save(any(FraudAlert.class))).thenReturn(savedAlert);
        when(fraudAlertMapper.toResponse(savedAlert)).thenReturn(responseMock);

        FraudAlertResponse result = fraudAlertService.takeAlertInReview(1L);

        assertNotNull(result);
        assertEquals(AlertStatus.IN_REVIEW, result.getStatus());
    }

    @Test
    void updateAlertStatus_ConfirmedFraud_UpdatesTransaction() {
        FinancialTransaction tx = new FinancialTransaction();
        tx.setId(10L);
        tx.setStatus(TransactionStatus.UNDER_REVIEW);

        FraudAlert alert = new FraudAlert();
        alert.setId(1L);
        alert.setStatus(AlertStatus.IN_REVIEW);
        alert.setTransaction(tx);

        FraudAlertStatusUpdateRequest request = new FraudAlertStatusUpdateRequest(AlertStatus.CONFIRMED_FRAUD, "Confirmed by bank");

        FraudAlert savedAlert = new FraudAlert();
        savedAlert.setId(1L);
        savedAlert.setStatus(AlertStatus.CONFIRMED_FRAUD);
        savedAlert.setAnalystComment("Confirmed by bank");
        savedAlert.setTransaction(tx);

        FraudAlertResponse responseMock = new FraudAlertResponse();
        responseMock.setId(1L);
        responseMock.setStatus(AlertStatus.CONFIRMED_FRAUD);
        responseMock.setAnalystComment("Confirmed by bank");

        when(fraudAlertRepository.findById(1L)).thenReturn(Optional.of(alert));
        when(transactionRepository.save(any(FinancialTransaction.class))).thenReturn(tx);
        when(fraudAlertRepository.save(any(FraudAlert.class))).thenReturn(savedAlert);
        when(fraudAlertMapper.toResponse(savedAlert)).thenReturn(responseMock);

        FraudAlertResponse result = fraudAlertService.updateAlertStatus(1L, request);

        assertNotNull(result);
        assertEquals(AlertStatus.CONFIRMED_FRAUD, result.getStatus());
        assertEquals("Confirmed by bank", result.getAnalystComment());
        assertEquals(TransactionStatus.REJECTED, tx.getStatus()); // Verify transaction status changed
    }
}
