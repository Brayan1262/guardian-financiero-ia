package com.brayan.guardianfinanciero.dto.dashboard;

import com.brayan.guardianfinanciero.model.enums.AlertStatus;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentAlertResponse {
    private Long id;
    private Long transactionId;
    private String customerFullName;
    private RiskLevel riskLevel;
    private Integer riskScore;
    private AlertStatus status;
    private LocalDateTime createdAt;
}
