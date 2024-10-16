package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID> {
    // Custom query methods
    List<RolePermission> findByRole_RoleId(UUID roleId);
}
