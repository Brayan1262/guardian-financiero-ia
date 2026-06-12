package com.brayan.guardianfinanciero.dto.risk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskRuleResponse {
    private Long id;
    private String ruleCode;
    private String name;
    private String description;
    private Integer score;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
