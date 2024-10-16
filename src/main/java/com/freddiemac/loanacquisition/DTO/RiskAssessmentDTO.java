package com.freddiemac.loanacquisition.DTO;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class RiskAssessmentDTO {
    private UUID assessmentId;
    private BigDecimal debtToIncomeRatio;
    private Integer creditScore;
    private String riskCategory; 
    private String remarks;
    private Timestamp assessmentDate;
    
    //No-args constructor
  	public RiskAssessmentDTO() {}
  	
    //All-args constructor
	public RiskAssessmentDTO(UUID assessmentId,
			BigDecimal debtToIncomeRatio, Integer creditScore, String riskCategory, String remarks,
			Timestamp assessmentDate) {
		super();
		this.assessmentId = assessmentId;
		this.debtToIncomeRatio = debtToIncomeRatio;
		this.creditScore = creditScore;
		this.riskCategory = riskCategory;
		this.remarks = remarks;
		this.assessmentDate = assessmentDate;
	}
	public UUID getAssessmentId() {
		return assessmentId;
	}
	public void setAssessmentId(UUID assessmentId) {
		this.assessmentId = assessmentId;
	}
	public BigDecimal getDebtToIncomeRatio() {
		return debtToIncomeRatio;
	}
	public void setDebtToIncomeRatio(BigDecimal debtToIncomeRatio) {
		this.debtToIncomeRatio = debtToIncomeRatio;
	}
	public Integer getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}
	public String getRiskCategory() {
		return riskCategory;
	}
	public void setRiskCategory(String riskCategory) {
		this.riskCategory = riskCategory;
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
