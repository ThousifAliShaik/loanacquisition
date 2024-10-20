package com.freddiemac.loanacquisition.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import com.freddiemac.loanacquisition.entity.RiskLevel;

public class LoanApplicationDTO {
    private UUID loanId;
    private BigDecimal loanAmount;
    private String loanType; 
    private String applicationStatus; 
    private RiskLevel riskLevel; 
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer requiredApprovalMatrix;
    private String finalApprovalStatus;
    private Timestamp finalApprovalTimestamp;
    private Boolean isActive;
    
    //No-args constructor
    public LoanApplicationDTO() {}
    
    //ALL-arg constructor
	public LoanApplicationDTO(UUID loanId, BigDecimal loanAmount, String loanType,
			String applicationStatus, RiskLevel riskLevel, Timestamp createdAt, Timestamp updatedAt,
			Integer requiredApprovalMatrix, String finalApprovalStatus,
			Timestamp finalApprovalTimestamp, Boolean isActive) {
		super();
		this.loanId = loanId;
		this.loanAmount = loanAmount;
		this.loanType = loanType;
		this.applicationStatus = applicationStatus;
		this.riskLevel = riskLevel;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.requiredApprovalMatrix = requiredApprovalMatrix;
		this.finalApprovalStatus = finalApprovalStatus;
		this.finalApprovalTimestamp = finalApprovalTimestamp;
		this.isActive = isActive;
	}
	
	public UUID getLoanId() {
		return loanId;
	}
	public void setLoanId(UUID loanId) {
		this.loanId = loanId;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getRiskLevel() {
		return riskLevel.toString();
	}
	public void setRiskLevel(RiskLevel riskLevel) {
		this.riskLevel = riskLevel;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Integer getRequiredApprovalMatrix() {
		return requiredApprovalMatrix;
	}
	public void setRequiredApprovalMatrix(Integer requiredApprovalMatrix) {
		this.requiredApprovalMatrix = requiredApprovalMatrix;
	}
	public String getFinalApprovalStatus() {
		return finalApprovalStatus;
	}
	public void setFinalApprovalStatus(String finalApprovalStatus) {
		this.finalApprovalStatus = finalApprovalStatus;
	}
	public Timestamp getFinalApprovalTimestamp() {
		return finalApprovalTimestamp;
	}
	public void setFinalApprovalTimestamp(Timestamp finalApprovalTimestamp) {
		this.finalApprovalTimestamp = finalApprovalTimestamp;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

    
}

