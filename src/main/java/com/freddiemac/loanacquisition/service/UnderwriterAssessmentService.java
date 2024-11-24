package com.freddiemac.loanacquisition.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freddiemac.loanacquisition.dto.UnderwriterAssessmentDTO;
import com.freddiemac.loanacquisition.entity.ApprovalStatus;
import com.freddiemac.loanacquisition.entity.AssessmentOutcome;
import com.freddiemac.loanacquisition.entity.IncomeVerificationStatus;
import com.freddiemac.loanacquisition.entity.LoanApproval;
import com.freddiemac.loanacquisition.entity.UnderwriterAssessment;
import com.freddiemac.loanacquisition.repository.LoanApplicationRepository;
import com.freddiemac.loanacquisition.repository.LoanApprovalRepository;
import com.freddiemac.loanacquisition.repository.UnderwriterAssessmentRepository;
import com.freddiemac.loanacquisition.security.UserPrincipal;
import com.freddiemac.loanacquisition.security.UserRepository;

@Service
public class UnderwriterAssessmentService {

    private final UnderwriterAssessmentRepository underwriterAssessmentRepository;
    
    private final LoanApplicationRepository loanApplicationRepository;
    
    private final LoanApprovalRepository loanApprovalRepository;
    
    private final UserRepository userRepository;

    public UnderwriterAssessmentService(UnderwriterAssessmentRepository underwriterAssessmentRepository,
    		LoanApplicationRepository loanApplicationRepository, UserRepository userRepository,
    		LoanApprovalRepository loanApprovalRepository) {
        this.underwriterAssessmentRepository = underwriterAssessmentRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanApprovalRepository = loanApprovalRepository;
    }

    // Convert UnderwriterAssessment entity to UnderwriterAssessmentDTO
    private UnderwriterAssessmentDTO convertToDTO(UnderwriterAssessment underwriterAssessment) {
        return new UnderwriterAssessmentDTO(
            underwriterAssessment.getUnderwriterAssessmentId(),
            underwriterAssessment.getLoan().getLoanId(),
            underwriterAssessment.getUnderwriter().getUserId(),
            underwriterAssessment.getLoanToValueRatio(),
            underwriterAssessment.getIncomeVerificationStatus().name(),
            underwriterAssessment.getAssessmentOutcome().name(),
            underwriterAssessment.getRemarks(),
            underwriterAssessment.getAssessmentDate()
        );
    }

    // Convert UnderwriterAssessmentDTO to UnderwriterAssessment entity
    private UnderwriterAssessment convertToEntity(UnderwriterAssessmentDTO underwriterAssessmentDTO) {
        UnderwriterAssessment underwriterAssessment = new UnderwriterAssessment();
        underwriterAssessment.setUnderwriterAssessmentId(underwriterAssessmentDTO.getUnderwriterAssessmentId());
        underwriterAssessment.setLoan(loanApplicationRepository.findById(underwriterAssessmentDTO.getLoanId()).get());
        underwriterAssessment.setUnderwriter(userRepository.findById(UserPrincipal.getCurrentUserId()).get());
        underwriterAssessment.setLoanToValueRatio(underwriterAssessmentDTO.getLoanToValueRatio());
        underwriterAssessment.setIncomeVerificationStatus(IncomeVerificationStatus.valueOf(underwriterAssessmentDTO.getIncomeVerificationStatus()));
        underwriterAssessment.setAssessmentOutcome(AssessmentOutcome.valueOf(underwriterAssessmentDTO.getAssessmentOutcome()));
        underwriterAssessment.setRemarks(underwriterAssessmentDTO.getRemarks());
        underwriterAssessment.setAssessmentDate(underwriterAssessmentDTO.getAssessmentDate());
        return underwriterAssessment;
    }

    @Transactional(readOnly = true)
    public List<UnderwriterAssessmentDTO> getAllUnderwriterAssessments() {
        return underwriterAssessmentRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UnderwriterAssessmentDTO getUnderwriterAssessmentById(UUID underwriterAssessmentId) {
        return underwriterAssessmentRepository.findById(underwriterAssessmentId)
            .map(this::convertToDTO)
            .orElse(null);
    }
    
    @Transactional(readOnly = true)
    public UnderwriterAssessmentDTO getUnderwriterAssessmentByLoanId(UUID loanId) {
        return underwriterAssessmentRepository.findByLoan_LoanId(loanId)
            .map(this::convertToDTO)
            .orElse(null);
    }

    @Transactional
    public UnderwriterAssessmentDTO createUnderwriterAssessment(UnderwriterAssessmentDTO underwriterAssessmentDTO) {
        UnderwriterAssessment underwriterAssessment = convertToEntity(underwriterAssessmentDTO);
        UnderwriterAssessment savedUnderwriterAssessment = underwriterAssessmentRepository.save(underwriterAssessment);
        
        LoanApproval loanApproval = loanApprovalRepository.findByLoan_LoanIdAndApprover_UserId(underwriterAssessmentDTO.getLoanId(), UserPrincipal.getCurrentUserId()).orElse(null);
    	if(loanApproval!=null) {
    		loanApproval.setApprovalDate(new Timestamp(System.currentTimeMillis()));
        	loanApproval.setApprovalStatus(ApprovalStatus.valueOf(
        			underwriterAssessmentDTO.getAssessmentOutcome().equals("APPROVE") ? "APPROVED" : "REJECTED"));
        	loanApproval.setRemarks(underwriterAssessmentDTO.getRemarks());
        	loanApprovalRepository.saveAndFlush(loanApproval);
        	
    	}
    	
        return convertToDTO(savedUnderwriterAssessment);
    }

    @Transactional
    public void deleteUnderwriterAssessment(UUID underwriterAssessmentId) {
        underwriterAssessmentRepository.deleteById(underwriterAssessmentId);
    }
    
}
