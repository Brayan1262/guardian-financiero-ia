package com.brayan.guardianfinanciero.dto.alert;

import com.brayan.guardianfinanciero.model.enums.AlertStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudAlertStatusUpdateRequest {

    @NotNull(message = "El estado de la alerta es obligatorio")
    private AlertStatus status;

    private String analystComment;
}
