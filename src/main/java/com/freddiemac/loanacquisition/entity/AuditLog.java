package com.freddiemac.loanacquisition.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id")
    private UUID logId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private AuditAction action;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @Column(name = "entity_id", nullable = false)
    private UUID entityId;

    @Column(name = "change_details", columnDefinition = "json")
    private String changeDetails;  // Assuming JSON string type; if using JSONB, map it appropriately

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

	public UUID getLogId() {
		return logId;
	}

	public void setLogId(UUID logId) {
		this.logId = logId;
	}

	public AuditAction getAction() {
		return action;
	}

	public void setAction(AuditAction action) {
		this.action = action;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
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
