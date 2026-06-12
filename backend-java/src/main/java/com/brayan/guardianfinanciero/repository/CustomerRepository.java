package com.brayan.guardianfinanciero.repository;

import com.brayan.guardianfinanciero.model.Customer;
import com.brayan.guardianfinanciero.model.enums.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByDocumentNumber(String documentNumber);
    boolean existsByDocumentNumber(String documentNumber);
    List<Customer> findByStatus(CustomerStatus status);
    List<Customer> findByFullNameContainingIgnoreCase(String fullName);
    long countByStatus(CustomerStatus status);
}
