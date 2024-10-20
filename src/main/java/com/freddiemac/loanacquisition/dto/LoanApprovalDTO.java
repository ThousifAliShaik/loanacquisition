package com.freddiemac.loanacquisition.dto;


import java.sql.Timestamp;
import java.util.UUID;

public class LoanApprovalDTO {
    private UUID approvalId;
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

    
}

