package com.brayan.guardianfinanciero.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardOverviewResponse {
    private DashboardSummaryResponse summary;
    private List<MetricCardResponse> metricCards;
    private DashboardChartsResponse charts;
    private List<RecentTransactionResponse> recentTransactions;
    private List<RecentAlertResponse> recentAlerts;
}
