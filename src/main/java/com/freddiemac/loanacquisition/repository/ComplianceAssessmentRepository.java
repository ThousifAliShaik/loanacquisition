package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.entity.ComplianceAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ComplianceAssessmentRepository extends JpaRepository<ComplianceAssessment, UUID> {
    // Custom query methods
    List<ComplianceAssessment> findByLoan_LoanId(UUID loanId);
}

