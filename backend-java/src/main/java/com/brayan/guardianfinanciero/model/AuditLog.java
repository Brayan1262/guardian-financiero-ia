package com.brayan.guardianfinanciero.model;

import com.brayan.guardianfinanciero.model.enums.AuditAction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;

    private String entityName;

    private Long entityId;

    @Column(columnDefinition = "TEXT")
    private String detail;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
