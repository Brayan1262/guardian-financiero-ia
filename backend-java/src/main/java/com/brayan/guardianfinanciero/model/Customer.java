package com.brayan.guardianfinanciero.model;

import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String documentNumber;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private CustomerStatus status = CustomerStatus.ACTIVE;

    @Builder.Default
    @Column(nullable = false)
    private Integer riskScore = 0;

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
