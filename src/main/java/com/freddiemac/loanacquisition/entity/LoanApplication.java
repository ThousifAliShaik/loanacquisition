package com.freddiemac.loanacquisition.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_id")
    private UUID loanId;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    @Column(name = "loan_amount", nullable = false)
    private BigDecimal loanAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", nullable = false)
    private ApplicationStatus applicationStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level")
    private RiskLevel riskLevel;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "required_approval_matrix", nullable = false)
    private Integer requiredApprovalMatrix;

    @Enumerated(EnumType.STRING)
    @Column(name = "final_approval_status", nullable = false, columnDefinition = "APPROVAL_STATUS DEFAULT 'PENDING'")
    private ApprovalStatus finalApprovalStatus;

    @ManyToOne
    @JoinColumn(name = "final_approver")
    private User finalApprover;

    @Column(name = "final_approval_timestamp")
    private Timestamp finalApprovalTimestamp;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

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

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public RiskLevel getRiskLevel() {
		return riskLevel;
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

	public ApprovalStatus getFinalApprovalStatus() {
		return finalApprovalStatus;
	}

	public void setFinalApprovalStatus(ApprovalStatus finalApprovalStatus) {
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
