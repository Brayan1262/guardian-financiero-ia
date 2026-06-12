package com.brayan.guardianfinanciero.model;

import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_analysis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "transaction_id", nullable = false)
    private FinancialTransaction transaction;

    private Double fraudProbability;

    @Enumerated(EnumType.STRING)
    private RiskLevel predictedRiskLevel;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(columnDefinition = "TEXT")
    private String recommendedAction;

    private String modelVersion;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
