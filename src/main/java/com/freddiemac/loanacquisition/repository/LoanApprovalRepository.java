package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.entity.LoanApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface LoanApprovalRepository extends JpaRepository<LoanApproval, UUID> {
    // Custom query methods
    List<LoanApproval> findByLoan_LoanId(UUID loanId);

}
