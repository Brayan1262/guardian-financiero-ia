package com.brayan.guardianfinanciero.repository;

import com.brayan.guardianfinanciero.model.FinancialTransaction;
import com.brayan.guardianfinanciero.model.enums.RiskLevel;
import com.brayan.guardianfinanciero.model.enums.TransactionChannel;
import com.brayan.guardianfinanciero.model.enums.TransactionStatus;
import com.brayan.guardianfinanciero.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {
    List<FinancialTransaction> findByCustomerId(Long customerId);
    List<FinancialTransaction> findByRiskLevel(RiskLevel riskLevel);
    List<FinancialTransaction> findByStatus(TransactionStatus status);
    List<FinancialTransaction> findByTransactionType(TransactionType transactionType);
    List<FinancialTransaction> findByChannel(TransactionChannel channel);
    List<FinancialTransaction> findByTransactionDateTimeBetween(LocalDateTime start, LocalDateTime end);
    Long countByStatus(TransactionStatus status);
    Long countByRiskLevel(RiskLevel riskLevel);
    Long countByCustomerIdAndTransactionDateTimeBetween(Long customerId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(f.amount) FROM FinancialTransaction f")
    BigDecimal sumTotalAmount();

    @Query("SELECT AVG(f.amount) FROM FinancialTransaction f")
    Double averageAmount();
}
