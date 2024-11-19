package com.freddiemac.loanacquisition.dto;


import java.sql.Timestamp;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class LoanApprovalDTO {
	
    private UUID approvalId;
    @NotNull
    private UUID approverId;
    @NotNull
    private UUID loanId;
    private String approverName;
    private String approverRoleName;
    private Integer approvalLevel;
    private String approvalStatus; 
    private String remarks;
    private Timestamp approvalDate;
    private Timestamp SLA;
    
    //No-args constructor
    
    public LoanApprovalDTO() {}
    
    //All-args constructor
    
	public LoanApprovalDTO(UUID approvalId, Integer approvalLevel,
			String approvalStatus, String remarks, Timestamp approvalDate, Timestamp sLA) {
		super();
		this.approvalId = approvalId;
		this.approvalLevel = approvalLevel;
		this.approvalStatus = approvalStatus;
		this.remarks = remarks;
		this.approvalDate = approvalDate;
		SLA = sLA;
	}
	
	public LoanApprovalDTO(UUID approvalId, @NotNull UUID approverId, @NotNull UUID loanId, String approverName,
			String approverRoleName, Integer approvalLevel, String approvalStatus, String remarks,
			Timestamp approvalDate, Timestamp sLA) {
		super();
		this.approvalId = approvalId;
		this.approverId = approverId;
		this.loanId = loanId;
		this.approverName = approverName;
		this.approverRoleName = approverRoleName;
		this.approvalLevel = approvalLevel;
		this.approvalStatus = approvalStatus;
		this.remarks = remarks;
		this.approvalDate = approvalDate;
		SLA = sLA;
	}

	public UUID getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(UUID approvalId) {
		this.approvalId = approvalId;
	}
	public Integer getApprovalLevel() {
		return approvalLevel;
	}
	public void setApprovalLevel(Integer approvalLevel) {
		this.approvalLevel = approvalLevel;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Timestamp getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Timestamp approvalDate) {
		this.approvalDate = approvalDate;
	}
	public Timestamp getSLA() {
		return SLA;
	}
	public void setSLA(Timestamp sLA) {
		SLA = sLA;
	}

	public UUID getApproverId() {
		return approverId;
	}

	public void setApproverId(UUID approverId) {
		this.approverId = approverId;
	}

	public UUID getLoanId() {
		return loanId;
	}

	public void setLoanId(UUID loanId) {
		this.loanId = loanId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproverRoleName() {
		return approverRoleName;
	}

	public void setApproverRoleName(String approverRoleName) {
		this.approverRoleName = approverRoleName;
	}

	
	
}

