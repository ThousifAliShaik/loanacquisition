package com.freddiemac.loanacquisition.dto;

import java.util.List;

public class LoanApplicationExtended {

	private LoanApplicationDTO loanApplication;
	
	private LenderDTO lenderDetails;
	
	private List<LoanApprovalDTO> loanApprovals;
	
	private UnderwriterAssessmentDTO underwriterAssessment;
	
	private RiskAssessmentDTO riskAssessment;
	
	private ComplianceAssessmentDTO complianceAssessment;

	public LoanApplicationDTO getLoanApplication() {
		return loanApplication;
	}

	public void setLoanApplication(LoanApplicationDTO loanApplication) {
		this.loanApplication = loanApplication;
	}

	public List<LoanApprovalDTO> getLoanApprovals() {
		return loanApprovals;
	}

	public void setLoanApprovals(List<LoanApprovalDTO> loanApprovals) {
		this.loanApprovals = loanApprovals;
	}

	public UnderwriterAssessmentDTO getUnderwriterAssessment() {
		return underwriterAssessment;
	}

	public void setUnderwriterAssessment(UnderwriterAssessmentDTO underwriterAssessment) {
		this.underwriterAssessment = underwriterAssessment;
	}

	public RiskAssessmentDTO getRiskAssessment() {
		return riskAssessment;
	}

	public void setRiskAssessment(RiskAssessmentDTO riskAssessment) {
		this.riskAssessment = riskAssessment;
	}

	public ComplianceAssessmentDTO getComplianceAssessment() {
		return complianceAssessment;
	}

	public void setComplianceAssessment(ComplianceAssessmentDTO complianceAssessment) {
		this.complianceAssessment = complianceAssessment;
	}

	
	public LenderDTO getLenderDetails() {
		return lenderDetails;
	}

	public void setLenderDetails(LenderDTO lenderDetails) {
		this.lenderDetails = lenderDetails;
	}

	public LoanApplicationExtended() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoanApplicationExtended(LoanApplicationDTO loanApplication, LenderDTO lenderDetails,
			List<LoanApprovalDTO> loanApprovals, UnderwriterAssessmentDTO underwriterAssessment, 
			RiskAssessmentDTO riskAssessment, ComplianceAssessmentDTO complianceAssessment) {
		super();
		this.loanApplication = loanApplication;
		this.lenderDetails = lenderDetails;
		this.loanApprovals = loanApprovals;
		this.underwriterAssessment = underwriterAssessment;
		this.riskAssessment = riskAssessment;
		this.complianceAssessment = complianceAssessment;
	}
	
}
