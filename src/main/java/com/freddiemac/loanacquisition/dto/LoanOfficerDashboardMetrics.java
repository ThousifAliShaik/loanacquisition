package com.freddiemac.loanacquisition.dto;

public class LoanOfficerDashboardMetrics {

	private long totalApplications;
	
	private long applicationsUnderReview;
	
	private long applicationsApproved;
	
	private long applicationsPendingFinalApproval;
	
	private long applicationsRejected;

	public long getTotalApplications() {
		return totalApplications;
	}

	public void setTotalApplications(long totalApplications) {
		this.totalApplications = totalApplications;
	}

	public long getApplicationsUnderReview() {
		return applicationsUnderReview;
	}

	public void setApplicationsUnderReview(long applicationsUnderReview) {
		this.applicationsUnderReview = applicationsUnderReview;
	}

	public long getApplicationsApproved() {
		return applicationsApproved;
	}

	public void setApplicationsApproved(long applicationsApproved) {
		this.applicationsApproved = applicationsApproved;
	}

	public long getApplicationsPendingFinalApproval() {
		return applicationsPendingFinalApproval;
	}

	public void setApplicationsPendingFinalApproval(long applicationsPendingFinalApproval) {
		this.applicationsPendingFinalApproval = applicationsPendingFinalApproval;
	}
	
	public long getApplicationsRejected() {
		return applicationsRejected;
	}

	public void setApplicationsRejected(long applicationsRejected) {
		this.applicationsRejected = applicationsRejected;
	}

	public LoanOfficerDashboardMetrics(long totalApplications, long applicationsUnderReview, long applicationsApproved,
			long applicationsPendingFinalApproval, long applicationsRejected) {
		super();
		this.totalApplications = totalApplications;
		this.applicationsUnderReview = applicationsUnderReview;
		this.applicationsApproved = applicationsApproved;
		this.applicationsPendingFinalApproval = applicationsPendingFinalApproval;
		this.applicationsRejected = applicationsRejected;
	}

	@Override
	public String toString() {
		return "LoanOfficerDashboardMetrics [totalApplications=" + totalApplications + ", applicationsUnderReview="
				+ applicationsUnderReview + ", applicationsApproved=" + applicationsApproved 
				+ ", applicationsRejected=" + applicationsRejected + ", applicationsPendingFinalApproval=" 
				+ applicationsPendingFinalApproval + "]";
	}

	public LoanOfficerDashboardMetrics() {
		super();
	}
	
	
}
