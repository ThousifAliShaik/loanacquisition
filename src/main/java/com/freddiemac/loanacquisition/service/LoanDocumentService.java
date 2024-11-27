package com.freddiemac.loanacquisition.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freddiemac.loanacquisition.dto.LoanDocumentDTO;
import com.freddiemac.loanacquisition.entity.DocumentType;
import com.freddiemac.loanacquisition.entity.LoanDocument;
import com.freddiemac.loanacquisition.repository.LoanApplicationRepository;
import com.freddiemac.loanacquisition.repository.LoanDocumentRepository;
import com.freddiemac.loanacquisition.security.UserRepository;

@Service
public class LoanDocumentService {

    private final LoanDocumentRepository loanDocumentRepository;
    
    private final LoanApplicationRepository loanApplicationRepository;
    
    private final UserRepository userRepository;

    public LoanDocumentService(LoanDocumentRepository loanDocumentRepository, 
    		LoanApplicationRepository loanApplicationRepository, UserRepository userRepository) {
        this.loanDocumentRepository = loanDocumentRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
    }

    // Convert LoanDocument entity to LoanDocumentDTO
    private LoanDocumentDTO convertToDTO(LoanDocument loanDocument) {
        return new LoanDocumentDTO(
            loanDocument.getDocumentId(),
            loanDocument.getLoan().getLoanId(),
            loanDocument.getDocumentName(),
            loanDocument.getDocumentType().name(),
            loanDocument.getFileContent(),
            loanDocument.getUploadedBy().getUserId(),
            loanDocument.getUploadedAt()
        );
    }

    // Convert LoanDocumentDTO to LoanDocument entity
    private LoanDocument convertToEntity(LoanDocumentDTO loanDocumentDTO) {
        LoanDocument loanDocument = new LoanDocument();
        loanDocument.setDocumentId(loanDocumentDTO.getDocumentId());
        loanDocument.setLoan(loanApplicationRepository.findById(loanDocumentDTO.getLoanId()).orElse(null));
        loanDocument.setDocumentName(loanDocumentDTO.getDocumentName());
        loanDocument.setDocumentType(DocumentType.valueOf(loanDocumentDTO.getDocumentType()));
        loanDocument.setFileContent(loanDocumentDTO.getFileContent());
        loanDocument.setUploadedBy(userRepository.findById(loanDocumentDTO.getUploadedBy()).orElse(null));
        loanDocument.setUploadedAt(loanDocumentDTO.getUploadedAt());
        return loanDocument;
    }

    
    public List<LoanDocumentDTO> getAllLoanDocuments() {
        return loanDocumentRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<LoanDocumentDTO> getLoanDocumentsByLoanId(UUID loanId) {
        return loanDocumentRepository.findByLoan_LoanId(loanId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public LoanDocumentDTO getLoanDocumentByDocumentId(UUID documentId) {
    	return loanDocumentRepository.findById(documentId).map(this::convertToDTO).orElse(null);
    }

   
    public LoanDocumentDTO createLoanDocument(LoanDocumentDTO loanDocumentDTO) {
        LoanDocument loanDocument = convertToEntity(loanDocumentDTO);
        LoanDocument savedLoanDocument = loanDocumentRepository.save(loanDocument);
        return convertToDTO(savedLoanDocument);
    }

   
    public void deleteLoanDocument(UUID documentId) {
        loanDocumentRepository.deleteById(documentId);
    }
    
    public void deleteLoanDocumentsByLoanId(UUID loanId) {
    	loanDocumentRepository.deleteByLoan_LoanId(loanId);
    }
    
    public void deleteLoanDocumentByLoanIdAndDocumentType(UUID loanId, DocumentType docType) {
    	loanDocumentRepository.deleteByLoan_LoanIdAndDocumentType(loanId, docType);
    }
}
