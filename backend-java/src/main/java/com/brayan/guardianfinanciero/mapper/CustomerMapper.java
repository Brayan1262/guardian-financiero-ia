package com.brayan.guardianfinanciero.mapper;

import com.brayan.guardianfinanciero.dto.customer.CustomerCreateRequest;
import com.brayan.guardianfinanciero.dto.customer.CustomerResponse;
import com.brayan.guardianfinanciero.dto.customer.CustomerUpdateRequest;
import com.brayan.guardianfinanciero.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerCreateRequest request) {
        if (request == null) {
            return null;
        }

        return Customer.builder()
                .documentNumber(request.getDocumentNumber())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
    }

    public CustomerResponse toResponse(Customer customer) {
        if (customer == null) {
            return null;
        }

        return CustomerResponse.builder()
                .id(customer.getId())
                .documentNumber(customer.getDocumentNumber())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .status(customer.getStatus())
                .riskScore(customer.getRiskScore())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    public void updateEntity(Customer customer, CustomerUpdateRequest request) {
        if (request != null) {
            customer.setFullName(request.getFullName());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
        }
    }
}
