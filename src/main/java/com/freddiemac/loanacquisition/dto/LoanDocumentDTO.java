package com.freddiemac.loanacquisition.dto;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;

public class LoanDocumentDTO {
    private UUID documentId;
    private UUID loanId;
    private String documentName;
    private String documentType; 
    private byte[] fileContent;
    private UUID uploadedBy;
    private Timestamp uploadedAt;
    
    //No-args constructor
    public LoanDocumentDTO() {}
    
    //All-args constructor
	public LoanDocumentDTO(UUID documentId, UUID loanId, String documentName, String documentType,
			byte[] fileContent, UUID uploadedBy,  Timestamp uploadedAt) {
		super();
		this.documentId = documentId;
		this.loanId = loanId;
		this.documentName = documentName;
		this.documentType = documentType;
		this.fileContent = fileContent;
		this.uploadedBy = uploadedBy;
		this.uploadedAt = uploadedAt;
	}
	public UUID getDocumentId() {
		return documentId;
	}
	public void setDocumentId(UUID documentId) {
		this.documentId = documentId;
	}
	
	public UUID getLoanId() {
		return loanId;
	}

	public void setLoanId(UUID loanId) {
		this.loanId = loanId;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public byte[] getFileContent() {
		return fileContent;
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
	
	public Timestamp getUploadedAt() {
		return uploadedAt;
	}
	public void setUploadedAt(Timestamp uploadedAt) {
		this.uploadedAt = uploadedAt;
	}
	
	public UUID getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(UUID uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	@Override
	public String toString() {
		return "LoanDocumentDTO [documentId=" + documentId + ", loanId=" + loanId + ", documentName=" + documentName
				+ ", documentType=" + documentType + ", fileContent=" + Arrays.toString(fileContent) + ", uploadedAt="
				+ uploadedAt + "]";
	}

    
}

