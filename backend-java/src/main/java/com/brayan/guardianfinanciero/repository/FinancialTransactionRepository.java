package com.brayan.guardianfinanciero.repository;

import com.brayan.guardianfinanciero.model.FinancialTransaction;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {
    List<FinancialTransaction> findByCustomerId(Long customerId);
    List<FinancialTransaction> findByRiskLevel(RiskLevel riskLevel);
    List<FinancialTransaction> findByStatus(TransactionStatus status);
}
