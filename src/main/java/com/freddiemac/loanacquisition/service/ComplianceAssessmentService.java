package com.freddiemac.loanacquisition.service;

import com.freddiemac.loanacquisition.DTO.ComplianceAssessmentDTO;
import com.freddiemac.loanacquisition.entity.ComplianceAssessment;
import com.freddiemac.loanacquisition.entity.ComplianceStatus;
import com.freddiemac.loanacquisition.repository.ComplianceAssessmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComplianceAssessmentService {

    private final ComplianceAssessmentRepository complianceAssessmentRepository;

    public ComplianceAssessmentService(ComplianceAssessmentRepository complianceAssessmentRepository) {
        this.complianceAssessmentRepository = complianceAssessmentRepository;
    }

    // Convert ComplianceAssessment entity to ComplianceAssessmentDTO
    private ComplianceAssessmentDTO convertToDTO(ComplianceAssessment complianceAssessment) {
        return new ComplianceAssessmentDTO(
            complianceAssessment.getComplianceId(),
            complianceAssessment.getComplianceStatus().name(),
            complianceAssessment.getRemarks(),
            complianceAssessment.getAssessmentDate()
        );
    }

    // Convert ComplianceAssessmentDTO to ComplianceAssessment entity
    private ComplianceAssessment convertToEntity(ComplianceAssessmentDTO complianceAssessmentDTO) {
        ComplianceAssessment complianceAssessment = new ComplianceAssessment();
        complianceAssessment.setComplianceId(complianceAssessmentDTO.getComplianceId());
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

    @Transactional
    public ComplianceAssessmentDTO createComplianceAssessment(ComplianceAssessmentDTO complianceAssessmentDTO) {
        ComplianceAssessment complianceAssessment = convertToEntity(complianceAssessmentDTO);
        ComplianceAssessment savedComplianceAssessment = complianceAssessmentRepository.save(complianceAssessment);
        return convertToDTO(savedComplianceAssessment);
    }

    @Transactional
    public void deleteComplianceAssessment(UUID complianceId) {
        complianceAssessmentRepository.deleteById(complianceId);
    }
}
