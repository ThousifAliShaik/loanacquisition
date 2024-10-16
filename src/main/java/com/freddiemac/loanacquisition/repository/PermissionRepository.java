package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    // Custom query methods
    Optional<Permission> findByPermissionName(String permissionName);
}
