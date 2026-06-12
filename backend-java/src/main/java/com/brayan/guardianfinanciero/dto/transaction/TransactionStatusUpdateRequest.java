package com.brayan.guardianfinanciero.dto.transaction;

import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatusUpdateRequest {

    @NotNull(message = "El estado de la transacción es obligatorio")
    private TransactionStatus status;
}
