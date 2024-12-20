package com.freddiemac.loanacquisition.entity;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;
import java.sql.Timestamp;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID userId;
    
    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "username", nullable = true, unique = true)
    private String username;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "last_login", nullable = true)
    private Timestamp lastLogin;

    @Column(name = "role_id", nullable = false)
    private UUID roleId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email",insertable = false, updatable = false)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "finalApprover")
    private Set<LoanApplication> loanApplications;

    @OneToMany(mappedBy = "uploadedBy")
    private Set<LoanDocument> loanDocuments;
    
    
	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Set<LoanApplication> getLoanApplications() {
		return loanApplications;
	}

	public void setLoanApplications(Set<LoanApplication> loanApplications) {
		this.loanApplications = loanApplications;
	}

	public Set<LoanDocument> getLoanDocuments() {
		return loanDocuments;
	}

	public void setLoanDocuments(Set<LoanDocument> loanDocuments) {
		this.loanDocuments = loanDocuments;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User() {
		super();
	}
    
    
}

