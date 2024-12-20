package com.freddiemac.loanacquisition.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.freddiemac.loanacquisition.entity.ApprovalStatus;
import com.freddiemac.loanacquisition.entity.LoanApproval;


public interface LoanApprovalRepository extends JpaRepository<LoanApproval, UUID> {
    // Custom query methods
    List<LoanApproval> findByLoan_LoanId(UUID loanId);
    
    List<LoanApproval> findByLoan_LoanIdIn(List<UUID>loanIdList);
    
    List<LoanApproval> findByApprover_UserId(UUID userId);
    
    Optional<LoanApproval> findByLoan_LoanIdAndApprover_UserId(UUID loanId, UUID userId);
    
    List<LoanApproval> findByApprover_UserIdAndApprovalStatus(UUID userId, ApprovalStatus approvalStatus);
   
    List<LoanApproval> findByApprover_UserIdAndApprovalStatusNot(UUID userId, ApprovalStatus approvalStatus);
    
    List<LoanApproval> findByLoan_LoanIdInAndApprovalLevelInAndApprovalStatusNot(List<UUID> loanIds, 
    		List<Integer> approvalLevels, ApprovalStatus status);
    
    List<LoanApproval> findByLoan_LoanIdInAndApprovalLevelInAndApprovalStatus(List<UUID> loanIds, 
    		List<Integer> approvalLevels, ApprovalStatus status);
    
    List<LoanApproval> findByLoan_LoanIdInAndApprover_UserId(List<UUID> loanIds, UUID userId);
    
    long countByApprover_UserIdAndApprovalStatus(UUID userId, ApprovalStatus approvalStatus);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM LoanApproval la WHERE la.loan.loanId = :loanId")
    void deleteByLoanId(UUID loanId);

}
