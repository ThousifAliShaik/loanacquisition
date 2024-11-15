package com.freddiemac.loanacquisition.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class NotificationDTO {
    private UUID notificationId;
    private String message;
    private String notificationType; 
    private Boolean isRead;
    private UUID loanId;
    private Timestamp createdAt;
    
    //No-args constructor
    public NotificationDTO() {}
    
    //All-args constructor
    
	public NotificationDTO(UUID notificationId, String message,
			String notificationType, Boolean isRead, Timestamp createdAt, UUID loanId) {
		super();
		this.notificationId = notificationId;
		this.message = message;
		this.notificationType = notificationType;
		this.isRead = isRead;
		this.createdAt = createdAt;
		this.loanId = loanId;
	}
	
	public UUID getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(UUID notificationId) {
		this.notificationId = notificationId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public UUID getLoanId() {
		return loanId;
	}

	public void setLoanId(UUID loanId) {
		this.loanId = loanId;
	}
   
}
