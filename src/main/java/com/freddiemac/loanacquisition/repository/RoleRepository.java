package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    // Custom query methods
    Optional<Role> findByRoleName(String roleName);
}