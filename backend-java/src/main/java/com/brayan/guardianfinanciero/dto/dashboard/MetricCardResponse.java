package com.brayan.guardianfinanciero.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricCardResponse {
    private String title;
    private String value;
    private String description;
    private String type; // ej. TRANSACTIONS, ALERTS, CUSTOMERS, AMOUNT
}
