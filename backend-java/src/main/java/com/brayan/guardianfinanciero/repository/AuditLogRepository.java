package com.brayan.guardianfinanciero.repository;

import com.brayan.guardianfinanciero.model.AuditLog;
import com.brayan.guardianfinanciero.model.enums.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByAction(AuditAction action);
}
