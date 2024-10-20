package com.freddiemac.loanacquisition.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class UserDTO {
    private UUID userId;
    private String username;
    private String password; 
    private String secretKey;
    private Timestamp lastLogin;
    private UUID roleId; 
    private Boolean isActive;
  
	
    //No-args constructor
    public UserDTO() {}
    
    //All-args constructor
    public UserDTO(UUID userId, String username, String password, String secretKey, Timestamp lastLogin, UUID roleId,
			Boolean isActive) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.secretKey = secretKey;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
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

}
