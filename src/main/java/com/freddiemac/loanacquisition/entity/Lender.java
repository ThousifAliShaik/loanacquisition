package com.freddiemac.loanacquisition.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lenders")
public class Lender {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lender_id")
    private UUID lenderId;
    
    @Column(name = "lender_name", nullable = true)
    private String lenderName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "lender_type", nullable = false)
    private LenderType lenderType;
    
    @Column(name = "registration_number", nullable = false, unique = true, updatable = false)
    private String registrationNumber;
    
    @CreationTimestamp
    @Column(name = "date_joined", nullable = false, updatable = false)
    private Timestamp dateJoined;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "risk_score", nullable = true)
    private Integer riskScore;

    @Column(name = "email", nullable = true, unique = true)
    private String email;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "website_url", nullable = true)
    private String websiteUrl;

	public UUID getLenderId() {
		return lenderId;
	}

	public void setLenderId(UUID lenderId) {
		this.lenderId = lenderId;
	}

	public String getLenderName() {
		return lenderName;
	}

	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}

	public LenderType getLenderType() {
		return lenderType;
	}

	public void setLenderType(LenderType lenderType) {
		this.lenderType = lenderType;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Timestamp getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Timestamp dateJoined) {
		this.dateJoined = dateJoined;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(Integer riskScore) {
		this.riskScore = riskScore;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getwebsiteUrl() {
		return websiteUrl;
	}

	public void setwebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public Lender() {
		super();
	}

	public Lender(UUID lenderId, String lenderName, LenderType lenderType, String registrationNumber,
			Timestamp dateJoined, Boolean isActive, Integer riskScore, String email, String address,
			String websiteUrl) {
		super();
		this.lenderId = lenderId;
		this.lenderName = lenderName;
		this.lenderType = lenderType;
		this.registrationNumber = registrationNumber;
		this.dateJoined = dateJoined;
		this.isActive = isActive;
		this.riskScore = riskScore;
		this.email = email;
		this.address = address;
		this.websiteUrl = websiteUrl;
	}

	@Override
	public String toString() {
		return "Lender [lenderId=" + lenderId + ", lenderName=" + lenderName + ", lenderType=" + lenderType
				+ ", registrationNumber=" + registrationNumber + ", dateJoined=" + dateJoined + ", isActive=" + isActive
				+ ", riskScore=" + riskScore + ", email=" + email + ", address=" + address + ", websiteUrl="
				+ websiteUrl + "]";
	}
    
}
