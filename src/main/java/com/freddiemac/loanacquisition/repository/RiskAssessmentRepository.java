package com.freddiemac.loanacquisition.repository;


import com.freddiemac.loanacquisition.entity.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, UUID> {
    // Custom query methods
    List<RiskAssessment> findByLoan_LoanId(UUID loanId);
}
