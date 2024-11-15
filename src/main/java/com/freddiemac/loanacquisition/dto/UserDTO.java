package com.freddiemac.loanacquisition.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class UserDTO {
    private UUID userId;
    private String fullName;
    private String email;
    private String username;
    private Timestamp lastLogin;
    private UUID roleId; 
    private Boolean isActive;
  
	
    //No-args constructor
    public UserDTO() {}
    
    //All-args constructor
    public UserDTO(UUID userId, String fullName, String email, String username, Timestamp lastLogin, UUID roleId,
			Boolean isActive) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.username = username;
		this.lastLogin = lastLogin;
		this.roleId = roleId;
		this.isActive = isActive;
		
	}
    
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	public UUID getRoleId() {
		return roleId;
	}
	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", email=" + email + ", username=" + username 
				+ ", lastLogin=" + lastLogin + ", roleId=" + roleId + ", isActive=" + isActive + "]";
	}
	
}
