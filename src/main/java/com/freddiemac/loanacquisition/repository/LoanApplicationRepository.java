package com.freddiemac.loanacquisition.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freddiemac.loanacquisition.entity.ApplicationStatus;
import com.freddiemac.loanacquisition.entity.ApprovalStatus;
import com.freddiemac.loanacquisition.entity.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, UUID> {
    // Custom query methods
    List<LoanApplication> findByFinalApprover_UserIdAndIsActiveTrue(UUID applicantId);
    List<LoanApplication> findByLoanIdIn(List<UUID> loanIds);
    List<LoanApplication> findByApplicationStatusAndIsActiveTrue(String status);
    List<LoanApplication> findByFinalApprovalStatusFalseAndIsActiveTrue();
    List<LoanApplication> findByFinalApprovalStatusTrueAndIsActiveTrue();
    List<LoanApplication> findByIsActiveTrue();
    List<LoanApplication> findByIsActiveFalse();
    long countByApplicationStatusAndFinalApprovalStatusAndIsActiveTrue(ApplicationStatus applicationStatus, ApprovalStatus status);
    long countByFinalApprovalStatusAndIsActiveTrue(ApprovalStatus status);
    long countByApplicationStatusAndIsActiveTrue(ApplicationStatus status);
    long countByIsActiveTrue();
    long countByIsActiveFalse();
}

