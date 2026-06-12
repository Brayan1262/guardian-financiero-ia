package com.brayan.guardianfinanciero.dto.risk;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskRuleCreateRequest {

    @NotBlank(message = "El código de la regla es obligatorio")
    private String ruleCode;

    @NotBlank(message = "El nombre de la regla es obligatorio")
    private String name;

    private String description;

    @NotNull(message = "El puntaje es obligatorio")
    @Min(value = 1, message = "El puntaje debe ser mayor a 0")
    private Integer score;

    @Builder.Default
    private Boolean active = true;
}
