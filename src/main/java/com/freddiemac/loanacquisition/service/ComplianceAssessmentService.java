package com.freddiemac.loanacquisition.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freddiemac.loanacquisition.dto.ComplianceAssessmentDTO;
import com.freddiemac.loanacquisition.entity.ApplicationStatus;
import com.freddiemac.loanacquisition.entity.ApprovalStatus;
import com.freddiemac.loanacquisition.entity.ComplianceAssessment;
import com.freddiemac.loanacquisition.entity.ComplianceStatus;
import com.freddiemac.loanacquisition.entity.LoanApplication;
import com.freddiemac.loanacquisition.entity.LoanApproval;
import com.freddiemac.loanacquisition.repository.ComplianceAssessmentRepository;
import com.freddiemac.loanacquisition.repository.LoanApplicationRepository;
import com.freddiemac.loanacquisition.repository.LoanApprovalRepository;
import com.freddiemac.loanacquisition.security.UserPrincipal;
import com.freddiemac.loanacquisition.security.UserRepository;

@Service
public class ComplianceAssessmentService {

    private final ComplianceAssessmentRepository complianceAssessmentRepository;
    
    private final LoanApplicationRepository loanApplicationRepository;
    
    private final LoanApprovalRepository loanApprovalRepository;
    
    private final UserRepository userRepository;
    
    public ComplianceAssessmentService(ComplianceAssessmentRepository complianceAssessmentRepository,
    		LoanApplicationRepository loanApplicationRepository, UserRepository userRepository,
    		LoanApprovalRepository loanApprovalRepository) {
        this.complianceAssessmentRepository = complianceAssessmentRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanApprovalRepository = loanApprovalRepository;
    }

    // Convert ComplianceAssessment entity to ComplianceAssessmentDTO
    private ComplianceAssessmentDTO convertToDTO(ComplianceAssessment complianceAssessment) {
        return new ComplianceAssessmentDTO(
            complianceAssessment.getComplianceId(),
            complianceAssessment.getLoan().getLoanId(),
            complianceAssessment.getComplianceOfficer().getUserId(),
            complianceAssessment.getComplianceStatus().name(),
            complianceAssessment.getRemarks(),
            complianceAssessment.getAssessmentDate()
        );
    }

    // Convert ComplianceAssessmentDTO to ComplianceAssessment entity
    private ComplianceAssessment convertToEntity(ComplianceAssessmentDTO complianceAssessmentDTO) {
        ComplianceAssessment complianceAssessment = new ComplianceAssessment();
        complianceAssessment.setComplianceId(complianceAssessmentDTO.getComplianceId());
        complianceAssessment.setLoan(loanApplicationRepository.findById(complianceAssessmentDTO.getLoanId()).orElse(null));
        complianceAssessment.setComplianceOfficer(userRepository.findById(UserPrincipal.getCurrentUserId()).orElse(null));
        complianceAssessment.setComplianceStatus(ComplianceStatus.valueOf(complianceAssessmentDTO.getComplianceStatus()));
        complianceAssessment.setRemarks(complianceAssessmentDTO.getRemarks());
        complianceAssessment.setAssessmentDate(complianceAssessmentDTO.getAssessmentDate());
        return complianceAssessment;
    }

    @Transactional(readOnly = true)
    public List<ComplianceAssessmentDTO> getAllComplianceAssessments() {
        return complianceAssessmentRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ComplianceAssessmentDTO getComplianceAssessmentById(UUID complianceId) {
        return complianceAssessmentRepository.findById(complianceId)
            .map(this::convertToDTO)
            .orElse(null);
    }
    
    @Transactional(readOnly = true)
    public ComplianceAssessmentDTO getComplianceAssessmentByLoanId(UUID loanId) {
        return complianceAssessmentRepository.findByLoan_LoanId(loanId)
            .map(this::convertToDTO)
            .orElse(null);
    }

    @Transactional
    public ComplianceAssessmentDTO createComplianceAssessment(ComplianceAssessmentDTO complianceAssessmentDTO) {
        ComplianceAssessment complianceAssessment = convertToEntity(complianceAssessmentDTO);
        ComplianceAssessment savedComplianceAssessment = complianceAssessmentRepository.save(complianceAssessment);
        
        LoanApproval loanApproval = loanApprovalRepository.findByLoan_LoanIdAndApprover_UserId(complianceAssessmentDTO.getLoanId(), UserPrincipal.getCurrentUserId()).orElse(null);
    	if(loanApproval!=null) {
    		loanApproval.setApprovalDate(new Timestamp(System.currentTimeMillis()));
        	loanApproval.setApprovalStatus(ApprovalStatus.valueOf(complianceAssessmentDTO.getComplianceStatus().equals("APPROVED") ? "APPROVED" : "REJECTED"));
        	loanApproval.setRemarks(complianceAssessmentDTO.getRemarks());
        	loanApprovalRepository.saveAndFlush(loanApproval);
    	}
    	
    	LoanApplication loanApplication = loanApplicationRepository.findById(complianceAssessment.getLoan().getLoanId()).orElse(null);
    	if(loanApplication!=null && loanApplication.getRequiredApprovalMatrix() == 3) {
    			loanApplication.setApplicationStatus(ApplicationStatus.SUBMITTED);
    			loanApplicationRepository.saveAndFlush(loanApplication);
    	}
    	
        return convertToDTO(savedComplianceAssessment);
    }

    @Transactional
    public void deleteComplianceAssessment(UUID complianceId) {
        complianceAssessmentRepository.deleteById(complianceId);
    }
}
