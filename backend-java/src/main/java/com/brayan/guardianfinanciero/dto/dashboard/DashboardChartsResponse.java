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
public class DashboardChartsResponse {
    private List<ChartDataResponse> transactionsByStatus;
    private List<ChartDataResponse> transactionsByRiskLevel;
    private List<ChartDataResponse> transactionsByChannel;
    private List<ChartDataResponse> alertsByStatus;
    private List<ChartDataResponse> alertsByRiskLevel;
    private List<ChartDataResponse> customersByStatus;
}
