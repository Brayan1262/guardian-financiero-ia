package com.brayan.guardianfinanciero.service;

import com.brayan.guardianfinanciero.dto.customer.CustomerCreateRequest;
import com.brayan.guardianfinanciero.dto.customer.CustomerResponse;
import com.brayan.guardianfinanciero.dto.customer.CustomerStatusUpdateRequest;
import com.brayan.guardianfinanciero.dto.customer.CustomerUpdateRequest;
import com.brayan.guardianfinanciero.exception.BusinessException;
import com.brayan.guardianfinanciero.exception.ResourceNotFoundException;
import com.brayan.guardianfinanciero.mapper.CustomerMapper;
import com.brayan.guardianfinanciero.model.Customer;
import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import com.brayan.guardianfinanciero.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        if (customerRepository.existsByDocumentNumber(request.getDocumentNumber())) {
            throw new BusinessException("Ya existe un cliente con el documento: " + request.getDocumentNumber());
        }

        Customer customer = customerMapper.toEntity(request);
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setRiskScore(0);

        customer = customerRepository.save(customer);
        return customerMapper.toResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        return customerMapper.toResponse(customer);
    }

    public CustomerResponse getCustomerByDocumentNumber(String documentNumber) {
        Customer customer = customerRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con documento: " + documentNumber));
        return customerMapper.toResponse(customer);
    }

    public List<CustomerResponse> getCustomersByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status).stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<CustomerResponse> searchCustomersByName(String fullName) {
        return customerRepository.findByFullNameContainingIgnoreCase(fullName).stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse updateCustomer(Long id, CustomerUpdateRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));

        customerMapper.updateEntity(customer, request);
        customer = customerRepository.save(customer);

        return customerMapper.toResponse(customer);
    }

    public CustomerResponse updateCustomerStatus(Long id, CustomerStatusUpdateRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));

        customer.setStatus(request.getStatus());
        customer = customerRepository.save(customer);

        return customerMapper.toResponse(customer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
        }
        // Eliminación física por ahora
        customerRepository.deleteById(id);
    }
}
