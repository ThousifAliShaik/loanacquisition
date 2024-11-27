package com.freddiemac.loanacquisition.repository;


import com.freddiemac.loanacquisition.entity.DocumentType;
import com.freddiemac.loanacquisition.entity.LoanDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface LoanDocumentRepository extends JpaRepository<LoanDocument, UUID> {
    // Custom query methods
    List<LoanDocument> findByLoan_LoanId(UUID loanId);
    
    void deleteByLoan_LoanId(UUID loanId);
    
    void deleteByLoan_LoanIdAndDocumentType(UUID loanId, DocumentType docType);
}

