package com.freddiemac.loanacquisition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freddiemac.loanacquisition.dto.NotificationDTO;
import com.freddiemac.loanacquisition.security.UserPrincipal;
import com.freddiemac.loanacquisition.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/all_notifications")
	public ResponseEntity<List<NotificationDTO>> getAllUserNotifications() {
		List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(UserPrincipal.getCurrentUserId());
		return ResponseEntity.ok(notifications);
	}
	
	@GetMapping("/unread_notifications_count")
	public ResponseEntity<Long> getUnreadUserNotificationsCount() {
		long notificationsCount = notificationService.getUnreadNotificationCountByUserId(UserPrincipal.getCurrentUserId());
		return ResponseEntity.ok(notificationsCount);
	}
	
	@PutMapping("/mark_notifications_read")
	public ResponseEntity<Void> markAllUserNotificationsRead() {
		notificationService.markAllNotificationsAsReadByUserId(UserPrincipal.getCurrentUserId());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/create_notification")
	public ResponseEntity<Void> createNewNotification(@Valid @RequestBody NotificationDTO notification) {
		NotificationDTO createdNotification = notificationService.createNotification(notification);
		if(createdNotification.getNotificationId()!=null)
			return ResponseEntity.ok().build();
		else
			return ResponseEntity.badRequest().build();
	}
}
