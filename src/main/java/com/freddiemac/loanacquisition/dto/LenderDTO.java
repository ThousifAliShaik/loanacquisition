package com.freddiemac.loanacquisition.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class LenderDTO {
	
    private UUID lenderId;
    
    private String lenderName;
    
    private String lenderType;
    
    private String registrationNumber;
    
    private Timestamp dateJoined;
    
    private Boolean isActive;

    private Integer riskScore;

    private String email;

    private String address;

    private String webisteUrl;

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

	public String getLenderType() {
		return lenderType;
	}

	public void setLenderType(String lenderType) {
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

	public String getWebisteUrl() {
		return webisteUrl;
	}

	public void setWebisteUrl(String webisteUrl) {
		this.webisteUrl = webisteUrl;
	}

	public LenderDTO(UUID lenderId, String lenderName, String lenderType, String registrationNumber,
			Timestamp dateJoined, Boolean isActive, Integer riskScore, String email, String address,
			String webisteUrl) {
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
		this.webisteUrl = webisteUrl;
	}

	public LenderDTO() {
		super();
	}

	@Override
	public String toString() {
		return "LenderDTO [lenderId=" + lenderId + ", lenderName=" + lenderName + ", lenderType=" + lenderType
				+ ", registrationNumber=" + registrationNumber + ", dateJoined=" + dateJoined + ", isActive=" + isActive
				+ ", riskScore=" + riskScore + ", email=" + email + ", address=" + address + ", webisteUrl="
				+ webisteUrl + "]";
	}
    
}
