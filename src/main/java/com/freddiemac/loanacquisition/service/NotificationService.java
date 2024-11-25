package com.freddiemac.loanacquisition.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freddiemac.loanacquisition.dto.NotificationDTO;
import com.freddiemac.loanacquisition.entity.Notification;
import com.freddiemac.loanacquisition.entity.NotificationType;
import com.freddiemac.loanacquisition.repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Convert Notification entity to NotificationDTO
    private NotificationDTO convertToDTO(Notification notification) {
        return new NotificationDTO(
            notification.getNotificationId(),
            notification.getMessage(),
            notification.getNotificationType().name(),
            notification.getIsRead(),
            notification.getCreatedAt(),
            notification.getLoan().getLoanId()
        );
    }

    // Convert NotificationDTO to Notification entity
    private Notification convertToEntity(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setNotificationId(notificationDTO.getNotificationId());
        notification.setMessage(notificationDTO.getMessage());
        notification.setNotificationType(NotificationType.valueOf(notificationDTO.getNotificationType()));
        notification.setIsRead(notificationDTO.getIsRead());
        notification.setCreatedAt(notificationDTO.getCreatedAt());
        return notification;
    }

    
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }


    public List<NotificationDTO> getNotificationsByUserId(UUID userId) {
        return notificationRepository.findByUser_UserIdOrderByCreatedAtDesc(userId).stream()
            .map(this::convertToDTO)
            .toList();
    }
    
    public long getUnreadNotificationCountByUserId(UUID userId) {
    	return notificationRepository.findByUser_UserIdAndIsReadFalse(userId).size();
    }
    
    @Transactional
    public void markAllNotificationsAsReadByUserId(UUID userId) {
    	notificationRepository.markNotificationsAsRead(userId);
    }

    @Transactional
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        Notification notification = convertToEntity(notificationDTO);
        Notification savedNotification = notificationRepository.save(notification);
        return convertToDTO(savedNotification);
    }
}
