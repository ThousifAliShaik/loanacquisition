package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, UUID> {
    // Custom query methods
    List<LoanApplication> findByApplicant_UserId(UUID applicantId);
    List<LoanApplication> findByApplicationStatus(String status);
}

