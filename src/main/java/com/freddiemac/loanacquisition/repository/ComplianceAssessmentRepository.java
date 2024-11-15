package com.freddiemac.loanacquisition.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freddiemac.loanacquisition.entity.ComplianceAssessment;

public interface ComplianceAssessmentRepository extends JpaRepository<ComplianceAssessment, UUID> {
    // Custom query methods
    Optional<ComplianceAssessment> findByLoan_LoanId(UUID loanId);
}

