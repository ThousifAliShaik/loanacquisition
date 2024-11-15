package com.freddiemac.loanacquisition.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RoleDTO {
    private UUID roleId;
    @NotNull
    @NotEmpty
    private String roleName;
    
    //No-args constructor
  	public RoleDTO() {}
    //All-args constructor
	public RoleDTO(UUID roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}
	public UUID getRoleId() {
		return roleId;
	}
	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "RoleDTO [roleId=" + roleId + ", roleName=" + roleName + "]";
	} 
    
}

