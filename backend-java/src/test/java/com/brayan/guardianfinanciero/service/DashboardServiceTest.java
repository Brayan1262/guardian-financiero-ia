package com.brayan.guardianfinanciero.service;

import com.brayan.guardianfinanciero.dto.dashboard.DashboardSummaryResponse;
import com.brayan.guardianfinanciero.repository.CustomerRepository;
import com.brayan.guardianfinanciero.repository.FinancialTransactionRepository;
import com.brayan.guardianfinanciero.repository.FraudAlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DashboardServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private FinancialTransactionRepository transactionRepository;

    @Mock
    private FraudAlertRepository fraudAlertRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSummary_Success() {
        when(customerRepository.count()).thenReturn(10L);
        when(transactionRepository.count()).thenReturn(50L);
        when(transactionRepository.getTotalTransactionAmount()).thenReturn(new BigDecimal("15000.00"));
        when(transactionRepository.getAverageTransactionAmount()).thenReturn(new BigDecimal("300.00"));
        when(fraudAlertRepository.count()).thenReturn(5L);

        DashboardSummaryResponse response = dashboardService.getSummary();

        assertNotNull(response);
        assertEquals(10L, response.getTotalCustomers());
        assertEquals(50L, response.getTotalTransactions());
        assertEquals(new BigDecimal("15000.00"), response.getTotalTransactionAmount());
        assertEquals(new BigDecimal("300.00"), response.getAverageTransactionAmount());
        assertEquals(5L, response.getTotalAlerts());
        assertNotNull(response.getGeneratedAt());
    }
}
