package com.brayan.guardianfinanciero.dto.transaction;

import com.brayan.guardianfinanciero.model.enums.TransactionChannel;
import com.brayan.guardianfinanciero.model.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateRequest {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long customerId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal amount;

    @NotNull(message = "El tipo de transacción es obligatorio")
    private TransactionType transactionType;

    @NotNull(message = "El canal de la transacción es obligatorio")
    private TransactionChannel channel;

    private String originLocation;

    private String destinationLocation;

    private String deviceId;

    @NotNull(message = "La fecha y hora de la transacción es obligatoria")
    private LocalDateTime transactionDateTime;
}
