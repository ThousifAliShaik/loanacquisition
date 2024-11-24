package com.freddiemac.loanacquisition.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class ComplianceAssessmentDTO {
	
    private UUID complianceId;
    private UUID loanId;
	private UUID userId;
    private String complianceStatus; 
    private String remarks;
    private Timestamp assessmentDate;
    
    //No-args constructor
    public ComplianceAssessmentDTO() {}
    
    //All-args constructor
	public ComplianceAssessmentDTO(UUID complianceId, UUID loanId, UUID userId,
			String complianceStatus, String remarks, Timestamp assessmentDate) {
		super();
		this.complianceId = complianceId;
		this.complianceStatus = complianceStatus;
		this.remarks = remarks;
		this.assessmentDate = assessmentDate;
	}
	public UUID getComplianceId() {
		return complianceId;
	}
	public void setComplianceId(UUID complianceId) {
		this.complianceId = complianceId;
	}
	public String getComplianceStatus() {
		return complianceStatus;
	}
	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Timestamp getAssessmentDate() {
		return assessmentDate;
	}
	public void setAssessmentDate(Timestamp assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public UUID getLoanId() {
		return loanId;
	}

	public void setLoanId(UUID loanId) {
		this.loanId = loanId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	
    
}
