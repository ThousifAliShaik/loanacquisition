package com.freddiemac.loanacquisition.service;


import com.freddiemac.loanacquisition.dto.RiskAssessmentDTO;
import com.freddiemac.loanacquisition.entity.RiskAssessment;
import com.freddiemac.loanacquisition.entity.RiskCategory;
import com.freddiemac.loanacquisition.repository.RiskAssessmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RiskAssessmentService {

    private final RiskAssessmentRepository riskAssessmentRepository;

    public RiskAssessmentService(RiskAssessmentRepository riskAssessmentRepository) {
        this.riskAssessmentRepository = riskAssessmentRepository;
    }

    // Convert RiskAssessment entity to RiskAssessmentDTO
    private RiskAssessmentDTO convertToDTO(RiskAssessment riskAssessment) {
        return new RiskAssessmentDTO(
            riskAssessment.getAssessmentId(),
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
        return convertToDTO(savedRiskAssessment);
    }

    @Transactional
    public void deleteRiskAssessment(UUID assessmentId) {
        riskAssessmentRepository.deleteById(assessmentId);
    }
}
