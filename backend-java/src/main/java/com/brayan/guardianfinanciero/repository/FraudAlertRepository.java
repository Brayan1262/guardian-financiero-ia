package com.brayan.guardianfinanciero.repository;

import com.brayan.guardianfinanciero.model.FraudAlert;
import com.brayan.guardianfinanciero.model.enums.AlertStatus;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FraudAlertRepository extends JpaRepository<FraudAlert, Long> {
    List<FraudAlert> findByStatus(AlertStatus status);
    List<FraudAlert> findByRiskLevel(RiskLevel riskLevel);
    List<FraudAlert> findByCustomerId(Long customerId);
    Optional<FraudAlert> findByTransactionId(Long transactionId);
}
