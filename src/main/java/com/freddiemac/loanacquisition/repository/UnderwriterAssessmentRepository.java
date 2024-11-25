package com.freddiemac.loanacquisition.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freddiemac.loanacquisition.entity.UnderwriterAssessment;

public interface UnderwriterAssessmentRepository extends JpaRepository<UnderwriterAssessment, UUID> {
    // Custom query methods
    Optional<UnderwriterAssessment> findByLoan_LoanId(UUID loanId);
    
    void deleteByLoan_LoanId(UUID loanId);
}
