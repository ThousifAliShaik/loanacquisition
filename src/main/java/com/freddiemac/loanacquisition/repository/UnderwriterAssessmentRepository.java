package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.entity.UnderwriterAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface UnderwriterAssessmentRepository extends JpaRepository<UnderwriterAssessment, UUID> {
    // Custom query methods
    List<UnderwriterAssessment> findByLoan_LoanId(UUID loanId);
}
