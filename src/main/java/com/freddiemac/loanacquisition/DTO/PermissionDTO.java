package com.freddiemac.loanacquisition.DTO;

import java.util.UUID;

public class PermissionDTO {
    private UUID permissionId;
    private String permissionName;
    
    //No-args constructor
    public PermissionDTO() {}
    
    //ALL-args constructor
	public PermissionDTO(UUID permissionId, String permissionName) {
		super();
		this.permissionId = permissionId;
		this.permissionName = permissionName;
	}
	
	public UUID getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(UUID permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	} 
    
    
}
