package com.freddiemac.loanacquisition.service;

import org.springframework.stereotype.Service;

import com.freddiemac.loanacquisition.DTO.LoanDocumentDTO;
import com.freddiemac.loanacquisition.entity.LoanDocument;
import com.freddiemac.loanacquisition.repository.LoanDocumentRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanDocumentService {

    private final LoanDocumentRepository loanDocumentRepository;

    public LoanDocumentService(LoanDocumentRepository loanDocumentRepository) {
        this.loanDocumentRepository = loanDocumentRepository;
    }

    // Convert LoanDocument entity to LoanDocumentDTO
    private LoanDocumentDTO convertToDTO(LoanDocument loanDocument) {
        return new LoanDocumentDTO(
            loanDocument.getDocumentId(),
            loanDocument.getDocumentName(),
            loanDocument.getDocumentType().name(),
            loanDocument.getFilePath(),
            loanDocument.getUploadedAt()
        );
    }

    // Convert LoanDocumentDTO to LoanDocument entity
    private LoanDocument convertToEntity(LoanDocumentDTO loanDocumentDTO) {
        LoanDocument loanDocument = new LoanDocument();
        loanDocument.setDocumentId(loanDocumentDTO.getDocumentId());
        loanDocument.setDocumentName(loanDocumentDTO.getDocumentName());
        loanDocument.setFilePath(loanDocumentDTO.getFilePath());
        loanDocument.setUploadedAt(loanDocumentDTO.getUploadedAt());
        return loanDocument;
    }

    
    public List<LoanDocumentDTO> getAllLoanDocuments() {
        return loanDocumentRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    
    public List<LoanDocumentDTO> getLoanDocumentsByLoanId(UUID loanId) {
        return loanDocumentRepository.findByLoan_LoanId(loanId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

   
    public LoanDocumentDTO createLoanDocument(LoanDocumentDTO loanDocumentDTO) {
        LoanDocument loanDocument = convertToEntity(loanDocumentDTO);
        LoanDocument savedLoanDocument = loanDocumentRepository.save(loanDocument);
        return convertToDTO(savedLoanDocument);
    }

   
    public void deleteLoanDocument(UUID documentId) {
        loanDocumentRepository.deleteById(documentId);
    }
}
