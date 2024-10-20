package com.freddiemac.loanacquisition.service;

import org.springframework.stereotype.Service;

import com.freddiemac.loanacquisition.dto.LoanApplicationDTO;
import com.freddiemac.loanacquisition.entity.ApplicationStatus;
import com.freddiemac.loanacquisition.entity.ApprovalStatus;
import com.freddiemac.loanacquisition.entity.LoanApplication;
import com.freddiemac.loanacquisition.entity.LoanType;
import com.freddiemac.loanacquisition.entity.RiskLevel;
import com.freddiemac.loanacquisition.repository.LoanApplicationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

   
    private LoanApplicationDTO convertToDTO(LoanApplication loanApplication) {
        return new LoanApplicationDTO(loanApplication.getLoanId(),
        		 loanApplication.getLoanAmount(),
                 loanApplication.getLoanType().name(),
                 loanApplication.getApplicationStatus().name(),
                 loanApplication.getRiskLevel(),
                 loanApplication.getCreatedAt(),
                 loanApplication.getUpdatedAt(),
                 loanApplication.getRequiredApprovalMatrix(),
                 loanApplication.getFinalApprovalStatus().name(),
                 loanApplication.getFinalApprovalTimestamp(),
                 loanApplication.getIsActive()
        );
    }

    // Convert LoanApplicationDTO to LoanApplication entity
    private LoanApplication convertToEntity(LoanApplicationDTO loanApplicationDTO) {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setLoanId(loanApplicationDTO.getLoanId());
        loanApplication.setLoanAmount(loanApplicationDTO.getLoanAmount());
        loanApplication.setLoanType(LoanType.valueOf(loanApplicationDTO.getLoanType()));
        loanApplication.setApplicationStatus(ApplicationStatus.valueOf(loanApplicationDTO.getApplicationStatus()));
        loanApplication.setRiskLevel(RiskLevel.valueOf(loanApplicationDTO.getRiskLevel()));
        loanApplication.setRequiredApprovalMatrix(loanApplicationDTO.getRequiredApprovalMatrix());
        loanApplication.setFinalApprovalStatus(ApprovalStatus.valueOf(loanApplicationDTO.getFinalApprovalStatus()));
        loanApplication.setFinalApprovalTimestamp(loanApplicationDTO.getFinalApprovalTimestamp());
        loanApplication.setIsActive(loanApplicationDTO.getIsActive());
        return loanApplication;
    }


    public List<LoanApplicationDTO> getAllLoanApplications() {
        return loanApplicationRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }


    public LoanApplicationDTO getLoanApplicationById(UUID loanId) {
        return loanApplicationRepository.findById(loanId)
            .map(this::convertToDTO)
            .orElse(null);
    }


    public LoanApplicationDTO createLoanApplication(LoanApplicationDTO loanApplicationDTO) {
        LoanApplication loanApplication = convertToEntity(loanApplicationDTO);
        LoanApplication savedLoanApplication = loanApplicationRepository.save(loanApplication);
        return convertToDTO(savedLoanApplication);
    }


    public LoanApplicationDTO updateLoanApplication(UUID loanId, LoanApplicationDTO loanApplicationDTO) {
        Optional<LoanApplication> existingLoanApplication = loanApplicationRepository.findById(loanId);
        if (existingLoanApplication.isPresent()) {
            LoanApplication updatedLoanApplication = convertToEntity(loanApplicationDTO);
            updatedLoanApplication.setLoanId(loanId);
            LoanApplication savedLoanApplication = loanApplicationRepository.save(updatedLoanApplication);
            return convertToDTO(savedLoanApplication);
        }
        return null;
    }


    public void InValidateLoanApplication(UUID loanId) {
        Optional<LoanApplication>  loan= loanApplicationRepository.findById(loanId);
        if(loan.isPresent()) {
        	LoanApplication loanDisable=loan.get();
        	loanDisable.setIsActive(false);
        }
        
    }
}
