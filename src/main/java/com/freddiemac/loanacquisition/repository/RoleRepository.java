package com.freddiemac.loanacquisition.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.freddiemac.loanacquisition.entity.Role;
import com.freddiemac.loanacquisition.entity.UserRole;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    // Custom query methods
	@Query(value = "SELECT * FROM roles WHERE role_name = CAST(:roleName AS user_roles)", nativeQuery = true)
	Optional<Role> findByRoleName(@Param("roleName") String roleName);


}