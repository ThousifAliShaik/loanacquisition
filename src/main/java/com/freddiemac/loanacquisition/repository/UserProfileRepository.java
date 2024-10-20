package com.freddiemac.loanacquisition.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freddiemac.loanacquisition.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    // Custom query methods
    Optional<UserProfile> findByEmail(String email);
    List<UserProfile> findByUserIsActiveTrue();
    List<UserProfile> findByUserIsActiveFalse();
}

