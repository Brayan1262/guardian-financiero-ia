package com.brayan.guardianfinanciero.service;

import com.brayan.guardianfinanciero.dto.transaction.TransactionCreateRequest;
import com.brayan.guardianfinanciero.dto.transaction.TransactionResponse;
import com.brayan.guardianfinanciero.exception.BusinessException;
import com.brayan.guardianfinanciero.mapper.TransactionMapper;
import com.brayan.guardianfinanciero.model.Customer;
import com.brayan.guardianfinanciero.model.FinancialTransaction;
import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import com.brayan.guardianfinanciero.model.enums.TransactionChannel;
import com.brayan.guardianfinanciero.model.enums.TransactionType;
import com.brayan.guardianfinanciero.repository.CustomerRepository;
import com.brayan.guardianfinanciero.repository.FinancialTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    @Mock
    private FinancialTransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransaction_CustomerActive_Success() {
        TransactionCreateRequest request = new TransactionCreateRequest(1L, new BigDecimal("150.00"), TransactionType.TRANSFER, TransactionChannel.WEB, "Lima", "Cusco", "Device1", LocalDateTime.now());
        
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setStatus(CustomerStatus.ACTIVE);

        FinancialTransaction mappedTx = new FinancialTransaction();
        mappedTx.setAmount(new BigDecimal("150.00"));

        FinancialTransaction savedTx = new FinancialTransaction();
        savedTx.setId(100L);
        savedTx.setAmount(new BigDecimal("150.00"));

        TransactionResponse responseMock = new TransactionResponse();
        responseMock.setId(100L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(transactionMapper.toEntity(request, customer)).thenReturn(mappedTx);
        when(transactionRepository.save(any(FinancialTransaction.class))).thenReturn(savedTx);
        when(transactionMapper.toResponse(savedTx)).thenReturn(responseMock);

        TransactionResponse result = transactionService.createTransaction(request);

        assertNotNull(result);
        assertEquals(100L, result.getId());
    }

    @Test
    void createTransaction_CustomerBlocked_ThrowsException() {
        TransactionCreateRequest request = new TransactionCreateRequest(1L, new BigDecimal("150.00"), TransactionType.TRANSFER, TransactionChannel.WEB, "Lima", "Cusco", "Device1", LocalDateTime.now());
        
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setStatus(CustomerStatus.BLOCKED);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        assertThrows(BusinessException.class, () -> {
            transactionService.createTransaction(request);
        });
    }
}
