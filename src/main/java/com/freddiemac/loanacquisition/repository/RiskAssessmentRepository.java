package com.freddiemac.loanacquisition.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freddiemac.loanacquisition.entity.RiskAssessment;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, UUID> {
    // Custom query methods
    Optional<RiskAssessment> findByLoan_LoanId(UUID loanId);
}
