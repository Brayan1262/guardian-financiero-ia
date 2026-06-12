package com.brayan.guardianfinanciero.dto.customer;

import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatusUpdateRequest {

    @NotNull(message = "El estado es obligatorio")
    private CustomerStatus status;
}
