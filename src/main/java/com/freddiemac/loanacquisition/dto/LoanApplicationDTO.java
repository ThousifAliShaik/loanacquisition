package com.freddiemac.loanacquisition.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.freddiemac.loanacquisition.entity.RiskLevel;

public class LoanApplicationDTO {
    private UUID loanId;
    private UUID lenderId;
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
    
    private UUID underwriterId;
    private UUID riskAnalystId;
    private UUID complianceOfficerId;
    private UUID managerId;
    private UUID seniorManagerId;
    private List<LoanDocumentDTO> loanDocuments;
    
    //No-args constructor
    public LoanApplicationDTO() {}
    
    //ALL-arg constructor
	public LoanApplicationDTO(UUID loanId, UUID lenderId, BigDecimal loanAmount, String loanType,
			String applicationStatus, RiskLevel riskLevel, Timestamp createdAt, Timestamp updatedAt,
			Integer requiredApprovalMatrix, String finalApprovalStatus,
			Timestamp finalApprovalTimestamp, Boolean isActive,
			UUID underwriterId, UUID riskAnalystId, UUID complianceOfficerId,
			UUID managerId, UUID seniorManagerId, List<LoanDocumentDTO> loanDocuments) {
		super();
		this.loanId = loanId;
		this.lenderId = lenderId;
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
		this.underwriterId = underwriterId;
		this.riskAnalystId = riskAnalystId;
		this.complianceOfficerId = complianceOfficerId;
		this.managerId = managerId;
		this.seniorManagerId = seniorManagerId;
		this.loanDocuments = loanDocuments;
	}
	
	public LoanApplicationDTO(UUID loanId, UUID lenderId, BigDecimal loanAmount, String loanType,
			String applicationStatus, RiskLevel riskLevel, Timestamp createdAt, Timestamp updatedAt,
			Integer requiredApprovalMatrix, String finalApprovalStatus,
			Timestamp finalApprovalTimestamp, Boolean isActive, List<LoanDocumentDTO> loanDocuments) {
		super();
		this.loanId = loanId;
		this.lenderId = lenderId;
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
		this.loanDocuments = loanDocuments;
	}
	
	public UUID getLoanId() {
		return loanId;
	}
	public void setLoanId(UUID loanId) {
		this.loanId = loanId;
	}
	
	public UUID getLenderId() {
		return lenderId;
	}

	public void setLenderId(UUID lenderId) {
		this.lenderId = lenderId;
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

	public UUID getUnderwriterId() {
		return underwriterId;
	}

	public void setUnderwriterId(UUID underwriterId) {
		this.underwriterId = underwriterId;
	}

	public UUID getRiskAnalystId() {
		return riskAnalystId;
	}

	public void setRiskAnalystId(UUID riskAnalystId) {
		this.riskAnalystId = riskAnalystId;
	}

	public UUID getComplianceOfficerId() {
		return complianceOfficerId;
	}

	public void setComplianceOfficerId(UUID complianceOfficerId) {
		this.complianceOfficerId = complianceOfficerId;
	}

	public UUID getManagerId() {
		return managerId;
	}

	public void setManagerId(UUID managerId) {
		this.managerId = managerId;
	}

	public UUID getSeniorManagerId() {
		return seniorManagerId;
	}

	public void setSeniorManagerId(UUID seniorManagerId) {
		this.seniorManagerId = seniorManagerId;
	}
	
	public List<LoanDocumentDTO> getLoanDocuments() {
		return loanDocuments;
	}

	public void setLoanDocuments(List<LoanDocumentDTO> loanDocuments) {
		this.loanDocuments = loanDocuments;
	}

	@Override
	public String toString() {
		return "LoanApplicationDTO [loanId=" + loanId + ", lenderId=" + lenderId + ", loanAmount=" + loanAmount
				+ ", loanType=" + loanType + ", applicationStatus=" + applicationStatus + ", riskLevel=" + riskLevel
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", requiredApprovalMatrix="
				+ requiredApprovalMatrix + ", finalApprovalStatus=" + finalApprovalStatus + ", finalApprovalTimestamp="
				+ finalApprovalTimestamp + ", isActive=" + isActive + ", underwriterId=" + underwriterId
				+ ", riskAnalystId=" + riskAnalystId + ", complianceOfficerId=" + complianceOfficerId + ", managerId="
				+ managerId + ", seniorManagerId=" + seniorManagerId + "]";
	}

}

