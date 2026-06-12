package com.brayan.guardianfinanciero.model;

import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionChannel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import com.brayan.guardianfinanciero.model.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionChannel channel;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;

    private String originLocation;

    private String destinationLocation;

    private String deviceId;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime transactionDateTime;

    @Builder.Default
    @Column(nullable = false)
    private Integer riskScore = 0;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private RiskLevel riskLevel = RiskLevel.LOW;

    @Column(columnDefinition = "TEXT")
    private String riskExplanation;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
