package com.freddiemac.loanacquisition.service;

import com.freddiemac.loanacquisition.DTO.UnderwriterAssessmentDTO;
import com.freddiemac.loanacquisition.entity.AssessmentOutcome;
import com.freddiemac.loanacquisition.entity.IncomeVerificationStatus;
import com.freddiemac.loanacquisition.entity.UnderwriterAssessment;
import com.freddiemac.loanacquisition.repository.UnderwriterAssessmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UnderwriterAssessmentService {

    private final UnderwriterAssessmentRepository underwriterAssessmentRepository;

    public UnderwriterAssessmentService(UnderwriterAssessmentRepository underwriterAssessmentRepository) {
        this.underwriterAssessmentRepository = underwriterAssessmentRepository;
    }

    // Convert UnderwriterAssessment entity to UnderwriterAssessmentDTO
    private UnderwriterAssessmentDTO convertToDTO(UnderwriterAssessment underwriterAssessment) {
        return new UnderwriterAssessmentDTO(
            underwriterAssessment.getUnderwriterAssessmentId(),
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

    @Transactional
    public UnderwriterAssessmentDTO createUnderwriterAssessment(UnderwriterAssessmentDTO underwriterAssessmentDTO) {
        UnderwriterAssessment underwriterAssessment = convertToEntity(underwriterAssessmentDTO);
        UnderwriterAssessment savedUnderwriterAssessment = underwriterAssessmentRepository.save(underwriterAssessment);
        return convertToDTO(savedUnderwriterAssessment);
    }

    @Transactional
    public void deleteUnderwriterAssessment(UUID underwriterAssessmentId) {
        underwriterAssessmentRepository.deleteById(underwriterAssessmentId);
    }
}
