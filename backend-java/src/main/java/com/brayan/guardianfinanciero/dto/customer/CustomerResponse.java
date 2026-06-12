package com.brayan.guardianfinanciero.dto.customer;

import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Long id;
    private String documentNumber;
    private String fullName;
    private String email;
    private String phone;
    private CustomerStatus status;
    private Integer riskScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
