package com.freddiemac.loanacquisition.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.sql.Timestamp;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "report_id")
    private UUID reportId;

    @ManyToOne
    @JoinColumn(name = "generated_by", nullable = false)
    private User generatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "generated_at", nullable = false)
    private Timestamp generatedAt;

	public UUID getReportId() {
		return reportId;
	}

	public void setReportId(UUID reportId) {
		this.reportId = reportId;
	}

	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
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
