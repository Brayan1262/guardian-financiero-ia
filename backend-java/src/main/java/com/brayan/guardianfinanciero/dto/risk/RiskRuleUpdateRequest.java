package com.brayan.guardianfinanciero.dto.risk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskRuleUpdateRequest {
    private String name;
    private String description;
    private Integer score;
    private Boolean active;
}
