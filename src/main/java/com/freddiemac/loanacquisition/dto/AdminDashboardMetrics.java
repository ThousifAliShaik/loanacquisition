package com.freddiemac.loanacquisition.dto;


public class AdminDashboardMetrics {

	private long totalUsers;
	
	private long pendingRegistrations;
	
	private long activeUsers;
	
	private long disabledUsers;

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getPendingRegistrations() {
		return pendingRegistrations;
	}

	public void setPendingRegistrations(long pendingRegistrations) {
		this.pendingRegistrations = pendingRegistrations;
	}

	public long getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(long activeUsers) {
		this.activeUsers = activeUsers;
	}

	public long getDisabledUsers() {
		return disabledUsers;
	}

	public void setDisabledUsers(long disabledUsers) {
		this.disabledUsers = disabledUsers;
	}

	@Override
	public String toString() {
		return "AdminDashboardMetrics [totalUsers=" + totalUsers + ", pendingRegistrations=" + pendingRegistrations
				+ ", activeUsers=" + activeUsers + ", disabledUsers=" + disabledUsers + "]";
	}
	
}
