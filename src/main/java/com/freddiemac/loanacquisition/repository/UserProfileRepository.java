package com.freddiemac.loanacquisition.repository;

import com.freddiemac.loanacquisition.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    // Custom query methods
    Optional<UserProfile> findByEmail(String email);
}

