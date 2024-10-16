package com.freddiemac.loanacquisition.DTO;



import java.sql.Timestamp;
import java.util.UUID;

public class ReportDTO {
    private UUID reportId;
    private String reportType; 
    private String filePath;
    private Timestamp generatedAt;
    
  //No-args constructor
  	public ReportDTO() {}
    
    //All-args constructor
	public ReportDTO(UUID reportId, UserDTO generatedBy, String reportType, String filePath, Timestamp generatedAt) {
		super();
		this.reportId = reportId;
		this.reportType = reportType;
		this.filePath = filePath;
		this.generatedAt = generatedAt;
	}
	public UUID getReportId() {
		return reportId;
	}
	public void setReportId(UUID reportId) {
		this.reportId = reportId;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Timestamp getGeneratedAt() {
		return generatedAt;
	}
	public void setGeneratedAt(Timestamp generatedAt) {
		this.generatedAt = generatedAt;
	}

    
}
