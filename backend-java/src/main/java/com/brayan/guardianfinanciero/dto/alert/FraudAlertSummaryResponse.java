package com.brayan.guardianfinanciero.dto.alert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudAlertSummaryResponse {
    private Long totalAlerts;
    private Long pendingAlerts;
    private Long inReviewAlerts;
    private Long confirmedFraudAlerts;
    private Long falsePositiveAlerts;
    private Long resolvedAlerts;
    private Long highRiskAlerts;
    private Long criticalRiskAlerts;
}
