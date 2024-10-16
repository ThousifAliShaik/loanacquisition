package com.freddiemac.loanacquisition.DTO;

import java.util.UUID;

public class RoleDTO {
    private UUID roleId;
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
    
}

