package com.freddiemac.loanacquisition.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class AuditLogDTO {
    private UUID logId;
    private String action; 
    private String entityType;
    private UUID entityId;
    private String changeDetails; // Assuming JSON as String
    private Timestamp timestamp;
    
    //No-args constructor
    public AuditLogDTO() {}
    
    //All-args constructor
	public AuditLogDTO(UUID logId,  String action, String entityType, UUID entityId, String changeDetails,
			Timestamp timestamp) {
		super();
		this.logId = logId;
		this.action = action;
		this.entityType = entityType;
		this.entityId = entityId;
		this.changeDetails = changeDetails;
		this.timestamp = timestamp;
	}
	public UUID getLogId() {
		return logId;
	}
	public void setLogId(UUID logId) {
		this.logId = logId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public UUID getEntityId() {
		return entityId;
	}
	public void setEntityId(UUID entityId) {
		this.entityId = entityId;
	}
	public String getChangeDetails() {
		return changeDetails;
	}
	public void setChangeDetails(String changeDetails) {
		this.changeDetails = changeDetails;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

    
}
