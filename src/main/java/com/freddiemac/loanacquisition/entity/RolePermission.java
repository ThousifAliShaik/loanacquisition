package com.freddiemac.loanacquisition.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "role_permissions")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_permission_id")
    private UUID rolePermissionId;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_name", nullable = false)
    private PermissionName permissionName;

	public UUID getRolePermissionId() {
		return rolePermissionId;
	}

	public void setRolePermissionId(UUID rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
	}

	public PermissionName getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(PermissionName permissionName) {
		this.permissionName = permissionName;
	}
    
    
}
