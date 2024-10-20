package com.freddiemac.loanacquisition.service;

import com.freddiemac.loanacquisition.dto.AuditLogDTO;
import com.freddiemac.loanacquisition.entity.AuditAction;
import com.freddiemac.loanacquisition.entity.AuditLog;
import com.freddiemac.loanacquisition.entity.EntityType;
import com.freddiemac.loanacquisition.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // Convert AuditLog entity to AuditLogDTO
    private AuditLogDTO convertToDTO(AuditLog auditLog) {
        return new AuditLogDTO(
            auditLog.getLogId(),
            auditLog.getAction().name(),
            auditLog.getEntityType().name(),
            auditLog.getEntityId(),
            auditLog.getChangeDetails(),
            auditLog.getTimestamp()
        );
    }

    // Convert AuditLogDTO to AuditLog entity
    private AuditLog convertToEntity(AuditLogDTO auditLogDTO) {
        AuditLog auditLog = new AuditLog();
        auditLog.setLogId(auditLogDTO.getLogId());
        auditLog.setAction(AuditAction.valueOf(auditLogDTO.getAction()));
        auditLog.setEntityType(EntityType.valueOf(auditLogDTO.getEntityType()));
        auditLog.setEntityId(auditLogDTO.getEntityId());
        auditLog.setChangeDetails(auditLogDTO.getChangeDetails());
        auditLog.setTimestamp(auditLogDTO.getTimestamp());
        return auditLog;
    }

    @Transactional(readOnly = true)
    public List<AuditLogDTO> getAllAuditLogs() {
        return auditLogRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AuditLogDTO getAuditLogById(UUID logId) {
        return auditLogRepository.findById(logId)
            .map(this::convertToDTO)
            .orElse(null);
    }

    @Transactional
    public AuditLogDTO createAuditLog(AuditLogDTO auditLogDTO) {
        AuditLog auditLog = convertToEntity(auditLogDTO);
        AuditLog savedAuditLog = auditLogRepository.save(auditLog);
        return convertToDTO(savedAuditLog);
    }

    @Transactional
    public void deleteAuditLog(UUID logId) {
        auditLogRepository.deleteById(logId);
    }
}
