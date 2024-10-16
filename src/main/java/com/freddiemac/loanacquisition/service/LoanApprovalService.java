package com.freddiemac.loanacquisition.service;

import org.springframework.stereotype.Service;

import com.freddiemac.loanacquisition.DTO.LoanApprovalDTO;
import com.freddiemac.loanacquisition.entity.ApprovalStatus;
import com.freddiemac.loanacquisition.entity.LoanApproval;
import com.freddiemac.loanacquisition.repository.LoanApprovalRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class LoanApprovalService {

    private final LoanApprovalRepository loanApprovalRepository;

    public LoanApprovalService(LoanApprovalRepository loanApprovalRepository) {
        this.loanApprovalRepository = loanApprovalRepository;
    }

    // Convert LoanApproval entity to LoanApprovalDTO
    private LoanApprovalDTO convertToDTO(LoanApproval loanApproval) {
        return new LoanApprovalDTO(
            loanApproval.getApprovalId(),
            loanApproval.getApprovalLevel(),
            loanApproval.getApprovalStatus().name(),
            loanApproval.getRemarks(),
            loanApproval.getApprovalDate(),
            loanApproval.getSLA()
        );
    }

    // Convert LoanApprovalDTO to LoanApproval entity
    private LoanApproval convertToEntity(LoanApprovalDTO loanApprovalDTO) {
        LoanApproval loanApproval = new LoanApproval();
        loanApproval.setApprovalId(loanApprovalDTO.getApprovalId());
        loanApproval.setApprovalLevel(loanApprovalDTO.getApprovalLevel());
        loanApproval.setApprovalStatus(ApprovalStatus.valueOf(loanApprovalDTO.getApprovalStatus()));
        loanApproval.setRemarks(loanApprovalDTO.getRemarks());
        loanApproval.setApprovalDate(loanApprovalDTO.getApprovalDate());
        loanApproval.setSLA(loanApprovalDTO.getSLA());
        return loanApproval;
    }

    public List<LoanApprovalDTO> getAllLoanApprovals() {
        return loanApprovalRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    
    public LoanApprovalDTO getLoanApprovalById(UUID approvalId) {
        return loanApprovalRepository.findById(approvalId)
            .map(this::convertToDTO)
            .orElse(null);
    }

    public List<LoanApprovalDTO> getLoanApprovalByLoan_id(UUID loanId) {
        return loanApprovalRepository.findByLoan_LoanId(loanId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }


    
    public LoanApprovalDTO createLoanApproval(LoanApprovalDTO loanApprovalDTO) {
        LoanApproval loanApproval = convertToEntity(loanApprovalDTO);
        LoanApproval savedLoanApproval = loanApprovalRepository.save(loanApproval);
        return convertToDTO(savedLoanApproval);
    }

    
    public void deleteLoanApproval(UUID approvalId) {
        loanApprovalRepository.deleteById(approvalId);
    }
}
