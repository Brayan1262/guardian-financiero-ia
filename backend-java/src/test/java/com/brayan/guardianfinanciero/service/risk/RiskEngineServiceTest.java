package com.brayan.guardianfinanciero.service.risk;

import com.brayan.guardianfinanciero.model.Customer;
import com.brayan.guardianfinanciero.model.FinancialTransaction;
import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionChannel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import com.brayan.guardianfinanciero.model.enums.TransactionType;
import com.brayan.guardianfinanciero.repository.FinancialTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RiskEngineServiceTest {

    @Mock
    private FinancialTransactionRepository transactionRepository;

    @InjectMocks
    private RiskEngineService riskEngineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void evaluateTransaction_LowRisk_Success() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setStatus(CustomerStatus.ACTIVE);

        FinancialTransaction tx = FinancialTransaction.builder()
                .customer(customer)
                .amount(new BigDecimal("100.00"))
                .transactionType(TransactionType.PAYMENT)
                .channel(TransactionChannel.POS)
                .transactionDateTime(LocalDateTime.of(2026, 6, 12, 10, 0))
                .originLocation("Lima")
                .destinationLocation("Lima")
                .build();

        when(transactionRepository.countByCustomerIdAndTransactionDateTimeBetween(any(), any(), any())).thenReturn(1L);

        RiskEvaluationResult result = riskEngineService.evaluateTransaction(tx);

        assertNotNull(result);
        assertTrue(result.getRiskScore() < 30);
        assertEquals(RiskLevel.LOW, result.getRiskLevel());
        assertEquals(TransactionStatus.APPROVED, result.getTransactionStatus());
    }

    @Test
    void evaluateTransaction_HighAmount_ReturnsHighRisk() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setStatus(CustomerStatus.ACTIVE);

        FinancialTransaction tx = FinancialTransaction.builder()
                .customer(customer)
                .amount(new BigDecimal("12000.00")) // VERY_HIGH_AMOUNT (+45)
                .transactionType(TransactionType.TRANSFER) // TRANSFER_OPERATION (+10)
                .channel(TransactionChannel.WEB) // DIGITAL_CHANNEL (+10)
                .transactionDateTime(LocalDateTime.of(2026, 6, 12, 14, 0))
                .originLocation("Lima")
                .destinationLocation("Cusco") // DIFFERENT_LOCATION (+15)
                .build();

        when(transactionRepository.countByCustomerIdAndTransactionDateTimeBetween(any(), any(), any())).thenReturn(1L);

        RiskEvaluationResult result = riskEngineService.evaluateTransaction(tx);

        assertNotNull(result);
        assertEquals(80, result.getRiskScore()); // 45 + 10 + 10 + 15 = 80
        assertEquals(RiskLevel.HIGH, result.getRiskLevel());
        assertEquals(TransactionStatus.UNDER_REVIEW, result.getTransactionStatus());
        assertTrue(result.getTriggeredRules().contains("VERY_HIGH_AMOUNT"));
    }
}
