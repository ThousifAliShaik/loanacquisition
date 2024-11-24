package com.freddiemac.loanacquisition.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.sql.Timestamp;

@Entity
@Table(name = "compliance_assessments")
public class ComplianceAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "compliance_id")
    private UUID complianceId;

    @OneToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanApplication loan;

    @ManyToOne
    @JoinColumn(name = "compliance_officer_id", nullable = false)
    private User complianceOfficer;

    @Enumerated(EnumType.STRING)
    @Column(name = "compliance_status", nullable = false)
    private ComplianceStatus complianceStatus;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "assessment_date", nullable = false)
    private Timestamp assessmentDate;

	public UUID getComplianceId() {
		return complianceId;
	}

	public void setComplianceId(UUID complianceId) {
		this.complianceId = complianceId;
	}

	public ComplianceStatus getComplianceStatus() {
		return complianceStatus;
	}

	public void setComplianceStatus(ComplianceStatus complianceStatus) {
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

	public LoanApplication getLoan() {
		return loan;
	}

	public void setLoan(LoanApplication loan) {
		this.loan = loan;
	}

	public User getComplianceOfficer() {
		return complianceOfficer;
	}

	public void setComplianceOfficer(User complianceOfficer) {
		this.complianceOfficer = complianceOfficer;
	}
    
    
}
