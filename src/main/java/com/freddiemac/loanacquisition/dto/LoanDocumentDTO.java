package com.freddiemac.loanacquisition.dto;


import java.sql.Timestamp;
import java.util.UUID;

public class LoanDocumentDTO {
    private UUID documentId;
    private String documentName;
    private String documentType; 
    private String filePath;
    private Timestamp uploadedAt;
    
    //No-args constructor
    public LoanDocumentDTO() {}
    
    //All-args constructor
	public LoanDocumentDTO(UUID documentId, String documentName, String documentType,
			String filePath,  Timestamp uploadedAt) {
		super();
		this.documentId = documentId;
		this.documentName = documentName;
		this.documentType = documentType;
		this.filePath = filePath;
		this.uploadedAt = uploadedAt;
	}
	public UUID getDocumentId() {
		return documentId;
	}
	public void setDocumentId(UUID documentId) {
		this.documentId = documentId;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Timestamp getUploadedAt() {
		return uploadedAt;
	}
	public void setUploadedAt(Timestamp uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

    
}

