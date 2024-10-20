package com.freddiemac.loanacquisition.dto;

import java.util.UUID;

public class RolePermissionDTO {
    private UUID rolePermissionId;
    private String permissionName;
    
    //No-args constructor
	public RolePermissionDTO() {}
	
    //All-args constructor
	public RolePermissionDTO(UUID rolePermissionId, String permissionName) {
		super();
		this.rolePermissionId = rolePermissionId;
		this.permissionName = permissionName;
	}
	public UUID getRolePermissionId() {
		return rolePermissionId;
	}
	public void setRolePermissionId(UUID rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	} 
    
    
}
