package com.freddiemac.loanacquisition.entity;


import jakarta.persistence.*;
import java.util.UUID;
import java.sql.Timestamp;

@Entity
@Table(name = "loan_approvals")
public class LoanApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "approval_id")
    private UUID approvalId;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanApplication loan;

    @ManyToOne
    @JoinColumn(name = "approver_id", nullable = false)
    private User approver;

    @Column(name = "approval_level", nullable = false)
    private Integer approvalLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false)
    private ApprovalStatus approvalStatus;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "approval_date")
    private Timestamp approvalDate;

    @Column(name = "SLA")
    private Timestamp SLA;

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

	public ApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalStatus approvalStatus) {
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

	public LoanApplication getLoan() {
		return loan;
	}

	public void setLoan(LoanApplication loan) {
		this.loan = loan;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}
    
    
}
