package com.freddiemac.loanacquisition.dto;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class UnderwriterAssessmentDTO {
    private UUID underwriterAssessmentId;
    private BigDecimal loanToValueRatio;
    private String incomeVerificationStatus; 
    private String assessmentOutcome; 
    private String remarks;
    private Timestamp assessmentDate;
    
    //No-args constructor
  	public UnderwriterAssessmentDTO() {}
  	
    //All-args constructor
	public UnderwriterAssessmentDTO(UUID underwriterAssessmentId, 
			BigDecimal loanToValueRatio, String incomeVerificationStatus, String assessmentOutcome, String remarks,
			Timestamp assessmentDate) {
		super();
		this.underwriterAssessmentId = underwriterAssessmentId;
		this.loanToValueRatio = loanToValueRatio;
		this.incomeVerificationStatus = incomeVerificationStatus;
		this.assessmentOutcome = assessmentOutcome;
		this.remarks = remarks;
		this.assessmentDate = assessmentDate;
	}
	public UUID getUnderwriterAssessmentId() {
		return underwriterAssessmentId;
	}
	public void setUnderwriterAssessmentId(UUID underwriterAssessmentId) {
		this.underwriterAssessmentId = underwriterAssessmentId;
	}
	public BigDecimal getLoanToValueRatio() {
		return loanToValueRatio;
	}
	public void setLoanToValueRatio(BigDecimal loanToValueRatio) {
		this.loanToValueRatio = loanToValueRatio;
	}
	public String getIncomeVerificationStatus() {
		return incomeVerificationStatus;
	}
	public void setIncomeVerificationStatus(String incomeVerificationStatus) {
		this.incomeVerificationStatus = incomeVerificationStatus;
	}
	public String getAssessmentOutcome() {
		return assessmentOutcome;
	}
	public void setAssessmentOutcome(String assessmentOutcome) {
		this.assessmentOutcome = assessmentOutcome;
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

