package com.freddiemac.loanacquisition.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "underwriter_assessments")
public class UnderwriterAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "underwriter_assessment_id")
    private UUID underwriterAssessmentId;

    @OneToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanApplication loan;

    @ManyToOne
    @JoinColumn(name = "underwriter_id", nullable = false)
    private User underwriter;

    @Column(name = "loan_to_value_ratio", nullable = false)
    private BigDecimal loanToValueRatio;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_verification_status", nullable = false)
    private IncomeVerificationStatus incomeVerificationStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "assessment_outcome", nullable = false)
    private AssessmentOutcome assessmentOutcome;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "assessment_date", nullable = false)
    private Timestamp assessmentDate;

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

	public IncomeVerificationStatus getIncomeVerificationStatus() {
		return incomeVerificationStatus;
	}

	public void setIncomeVerificationStatus(IncomeVerificationStatus incomeVerificationStatus) {
		this.incomeVerificationStatus = incomeVerificationStatus;
	}

	public AssessmentOutcome getAssessmentOutcome() {
		return assessmentOutcome;
	}

	public void setAssessmentOutcome(AssessmentOutcome assessmentOutcome) {
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
