package com.brayan.guardianfinanciero.service;

import com.brayan.guardianfinanciero.dto.customer.CustomerCreateRequest;
import com.brayan.guardianfinanciero.dto.customer.CustomerResponse;
import com.brayan.guardianfinanciero.mapper.CustomerMapper;
import com.brayan.guardianfinanciero.model.Customer;
import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import com.brayan.guardianfinanciero.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_Success() {
        CustomerCreateRequest request = new CustomerCreateRequest("74859612", "Juan Pérez", "juan@test.com", "987654321");
        Customer mappedCustomer = new Customer();
        mappedCustomer.setDocumentNumber("74859612");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setDocumentNumber("74859612");
        savedCustomer.setStatus(CustomerStatus.ACTIVE);
        savedCustomer.setRiskScore(0);

        CustomerResponse expectedResponse = new CustomerResponse();
        expectedResponse.setId(1L);
        expectedResponse.setDocumentNumber("74859612");

        when(customerRepository.existsByDocumentNumber("74859612")).thenReturn(false);
        when(customerMapper.toEntity(request)).thenReturn(mappedCustomer);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerMapper.toResponse(savedCustomer)).thenReturn(expectedResponse);

        CustomerResponse response = customerService.createCustomer(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("74859612", response.getDocumentNumber());
    }
}
