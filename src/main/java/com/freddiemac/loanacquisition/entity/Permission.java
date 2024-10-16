package com.freddiemac.loanacquisition.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "permission_id")
    private UUID permissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_name", nullable = false, unique = true)
    private PermissionName permissionName;

	public UUID getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(UUID permissionId) {
		this.permissionId = permissionId;
	}

	public PermissionName getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(PermissionName permissionName) {
		this.permissionName = permissionName;
	}
    
    
}
