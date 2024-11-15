package com.freddiemac.loanacquisition.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.freddiemac.loanacquisition.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    // Custom query methods
    List<Notification> findByUser_UserIdAndIsReadFalse(UUID userId);
    
    List<Notification> findByUser_UserIdOrderByCreatedAtDesc(UUID userId);
    
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user.userId = :userId AND n.isRead = false")
    void markNotificationsAsRead(@Param("userId") UUID userId);
}
