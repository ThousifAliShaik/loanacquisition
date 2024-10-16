package com.freddiemac.loanacquisition.repository;


import com.freddiemac.loanacquisition.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    // Custom query methods
    List<Notification> findByUser_UserId(UUID userId);
}
