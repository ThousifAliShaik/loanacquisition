package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
    // Custom query methods
    List<Report> findByGeneratedBy_UserId(UUID userId);
}
