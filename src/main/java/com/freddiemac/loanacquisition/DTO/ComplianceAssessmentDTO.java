package com.freddiemac.loanacquisition.DTO;

import java.sql.Timestamp;
import java.util.UUID;

public class ComplianceAssessmentDTO {
    private UUID complianceId;
    private String complianceStatus; 
    private String remarks;
    private Timestamp assessmentDate;
    
    //No-args constructor
    public ComplianceAssessmentDTO() {}
    
    //All-args constructor
	public ComplianceAssessmentDTO(UUID complianceId,
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

    
}
