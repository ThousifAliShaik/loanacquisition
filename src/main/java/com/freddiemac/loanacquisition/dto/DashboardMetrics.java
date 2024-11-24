package com.freddiemac.loanacquisition.dto;

public class DashboardMetrics {

	private long totalApplications;
	
	private long applicationsUnderReview;
	
	private long applicationsApproved;
	
	private long applicationsPendingFinalApproval;
	
	private long applicationsRejected;
	
	private long applicationsPendingUserAction;

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
	
	public long getApplicationsPendingUserAction() {
		return applicationsPendingUserAction;
	}

	public void setApplicationsPendingUserAction(long applicationsPendingUserAction) {
		this.applicationsPendingUserAction = applicationsPendingUserAction;
	}

	@Override
	public String toString() {
		return "DashboardMetrics [totalApplications=" + totalApplications + ", applicationsUnderReview="
				+ applicationsUnderReview + ", applicationsApproved=" + applicationsApproved
				+ ", applicationsPendingFinalApproval=" + applicationsPendingFinalApproval + ", applicationsRejected="
				+ applicationsRejected + ", applicationsPendingUserAction=" + applicationsPendingUserAction + "]";
	}

	public DashboardMetrics(long totalApplications, long applicationsUnderReview, long applicationsApproved,
			long applicationsPendingFinalApproval, long applicationsRejected, long applicationsPendingUserAction) {
		super();
		this.totalApplications = totalApplications;
		this.applicationsUnderReview = applicationsUnderReview;
		this.applicationsApproved = applicationsApproved;
		this.applicationsPendingFinalApproval = applicationsPendingFinalApproval;
		this.applicationsRejected = applicationsRejected;
		this.applicationsPendingUserAction = applicationsPendingUserAction;
	}

	public DashboardMetrics() {
		super();
	}
}
