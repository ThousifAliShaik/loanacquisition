package com.freddiemac.loanacquisition.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "risk_assessments")
public class RiskAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assessment_id")
    private UUID assessmentId;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanApplication loan;

    @ManyToOne
    @JoinColumn(name = "underwriter_id", nullable = false)
    private User underwriter;

    @Column(name = "debt_to_income_ratio", nullable = false)
    private BigDecimal debtToIncomeRatio;

    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_category", nullable = false)
    private RiskCategory riskCategory;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "assessment_date", nullable = false)
    private Timestamp assessmentDate;

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

	public RiskCategory getRiskCategory() {
		return riskCategory;
	}

	public void setRiskCategory(RiskCategory riskCategory) {
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
