package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.dto.UserDTO;
import com.freddiemac.loanacquisition.entity.User;
import com.freddiemac.loanacquisition.entity.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // Custom query methods
    Optional<User> findByUsername(String username);

	List<User> findByIsActive(Boolean isActive);

	Collection<User> findByRoleId(UUID roleId);
}

