package com.brayan.guardianfinanciero.repository;

import com.brayan.guardianfinanciero.model.RiskRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiskRuleRepository extends JpaRepository<RiskRule, Long> {
    Optional<RiskRule> findByRuleCode(String ruleCode);
    List<RiskRule> findByActiveTrue();
}
