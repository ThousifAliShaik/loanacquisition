package com.freddiemac.loanacquisition.service;


import com.freddiemac.loanacquisition.dto.RiskAssessmentDTO;
import com.freddiemac.loanacquisition.entity.ApprovalStatus;
import com.freddiemac.loanacquisition.entity.LoanApproval;
import com.freddiemac.loanacquisition.entity.RiskAssessment;
import com.freddiemac.loanacquisition.entity.RiskCategory;
import com.freddiemac.loanacquisition.repository.LoanApplicationRepository;
import com.freddiemac.loanacquisition.repository.LoanApprovalRepository;
import com.freddiemac.loanacquisition.repository.RiskAssessmentRepository;
import com.freddiemac.loanacquisition.security.UserPrincipal;
import com.freddiemac.loanacquisition.security.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RiskAssessmentService {

    private final RiskAssessmentRepository riskAssessmentRepository;
    
    private final LoanApplicationRepository loanApplicationRepository;
    
    private final LoanApprovalRepository loanApprovalRepository;
    
    private final UserRepository userRepository;

    public RiskAssessmentService(RiskAssessmentRepository riskAssessmentRepository,
    		LoanApplicationRepository loanApplicationRepository, UserRepository userRepository,
    		LoanApprovalRepository loanApprovalRepository) {
        this.riskAssessmentRepository = riskAssessmentRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanApprovalRepository = loanApprovalRepository;
    }

    // Convert RiskAssessment entity to RiskAssessmentDTO
    private RiskAssessmentDTO convertToDTO(RiskAssessment riskAssessment) {
        return new RiskAssessmentDTO(
            riskAssessment.getAssessmentId(),
            riskAssessment.getLoan().getLoanId(),
            riskAssessment.getRiskAnalyst().getUserId(),
            riskAssessment.getDebtToIncomeRatio(),
            riskAssessment.getCreditScore(),
            riskAssessment.getRiskCategory().name(),
            riskAssessment.getRemarks(),
            riskAssessment.getAssessmentDate()
        );
    }

    // Convert RiskAssessmentDTO to RiskAssessment entity
    private RiskAssessment convertToEntity(RiskAssessmentDTO riskAssessmentDTO) {
        RiskAssessment riskAssessment = new RiskAssessment();
        riskAssessment.setAssessmentId(riskAssessmentDTO.getAssessmentId());
        riskAssessment.setLoan(loanApplicationRepository.findById(riskAssessmentDTO.getLoanId()).orElse(null));
        riskAssessment.setRiskAnalyst(userRepository.findById(UserPrincipal.getCurrentUserId()).orElse(null));
        riskAssessment.setDebtToIncomeRatio(riskAssessmentDTO.getDebtToIncomeRatio());
        riskAssessment.setCreditScore(riskAssessmentDTO.getCreditScore());
        riskAssessment.setRiskCategory(RiskCategory.valueOf(riskAssessmentDTO.getRiskCategory()));
        riskAssessment.setRemarks(riskAssessmentDTO.getRemarks());
        riskAssessment.setAssessmentDate(riskAssessmentDTO.getAssessmentDate());
        return riskAssessment;
    }

    @Transactional(readOnly = true)
    public List<RiskAssessmentDTO> getAllRiskAssessments() {
        return riskAssessmentRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RiskAssessmentDTO getRiskAssessmentById(UUID assessmentId) {
        return riskAssessmentRepository.findById(assessmentId)
            .map(this::convertToDTO)
            .orElse(null);
    }

    @Transactional(readOnly = true)
    public RiskAssessmentDTO getRiskAssessmentByLoanId(UUID loanId) {
        return riskAssessmentRepository.findByLoan_LoanId(loanId)
            .map(this::convertToDTO)
            .orElse(null);
    }
    
    @Transactional
    public RiskAssessmentDTO createRiskAssessment(RiskAssessmentDTO riskAssessmentDTO) {
        RiskAssessment riskAssessment = convertToEntity(riskAssessmentDTO);
        RiskAssessment savedRiskAssessment = riskAssessmentRepository.save(riskAssessment);
        
        LoanApproval loanApproval = loanApprovalRepository.findByLoan_LoanIdAndApprover_UserId(riskAssessmentDTO.getLoanId(), UserPrincipal.getCurrentUserId()).orElse(null);
    	if(loanApproval!=null) {
    		loanApproval.setApprovalDate(new Timestamp(System.currentTimeMillis()));
        	loanApproval.setApprovalStatus(ApprovalStatus.APPROVED);
        	loanApproval.setRemarks(riskAssessmentDTO.getRemarks());
        	loanApprovalRepository.saveAndFlush(loanApproval);
        	
    	}
    	
        return convertToDTO(savedRiskAssessment);
    }

    @Transactional
    public void deleteRiskAssessment(UUID assessmentId) {
        riskAssessmentRepository.deleteById(assessmentId);
    }
}
