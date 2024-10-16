package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    // Custom query methods
    List<AuditLog> findByUser_UserId(UUID userId);
}

