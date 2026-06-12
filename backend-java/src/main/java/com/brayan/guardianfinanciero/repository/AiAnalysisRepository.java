package com.brayan.guardianfinanciero.repository;

import com.brayan.guardianfinanciero.model.AiAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AiAnalysisRepository extends JpaRepository<AiAnalysis, Long> {
    Optional<AiAnalysis> findByTransactionId(Long transactionId);
}
