package com.freddiemac.loanacquisition.util.pdf;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.freddiemac.loanacquisition.dto.LoanApplicationExtended;
import com.freddiemac.loanacquisition.dto.LoanApprovalDTO;
import com.freddiemac.loanacquisition.service.UserService;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {

	@Autowired
	private UserService userService;
	
	public byte[] generateLoanApplicationPdf(LoanApplicationExtended loanApplicationExtended) {
		
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    Document document = new Document();

	    try {
	        PdfWriter.getInstance(document, byteArrayOutputStream);
	        document.open();

	        // Title
	        document.addTitle("Loan Acquisition Report: " + loanApplicationExtended.getLoanApplication().getLoanId());
	        document.add(new Paragraph("Loan Acquisition Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
	        document.add(new Paragraph("Confidential | Prepared for Internal Review", FontFactory.getFont(FontFactory.HELVETICA, 12)));
	        document.add(new Chunk(" "));
	        
	        // Loan Application Details
	        document.add(new Paragraph("Loan Application Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
	        document.add(new Chunk(" "));

	        
	        PdfPTable loanTable = new PdfPTable(2);
	        loanTable.setWidthPercentage(100);
	        loanTable.setSpacingBefore(10f);
	        loanTable.setSpacingAfter(10f);

	        loanTable.addCell("Loan ID:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getLoanId().toString());
	        loanTable.addCell("Loan Amount:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getLoanAmount().toString());
	        loanTable.addCell("Loan Type:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getLoanType());
	        loanTable.addCell("Application Status:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getApplicationStatus());
	        loanTable.addCell("Risk Level:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getRiskLevel());
	        loanTable.addCell("Created At:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getCreatedAt().toString());
	        loanTable.addCell("Updated At:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getUpdatedAt().toString());
	        loanTable.addCell("Required Approval Matrix:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getRequiredApprovalMatrix().toString());
	        loanTable.addCell("Final Approval Status:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getFinalApprovalStatus());
	        loanTable.addCell("Final Approval Timestamp:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getFinalApprovalTimestamp().toString());
	        loanTable.addCell("Is Active:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getIsActive().toString());
	        loanTable.addCell("Underwriter ID:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getUnderwriterId().toString());
	        loanTable.addCell("Risk Analyst ID:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getRiskAnalystId().toString());
	        loanTable.addCell("Compliance Officer ID:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getComplianceOfficerId().toString());
	        loanTable.addCell("Manager ID:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getManagerId().toString());
	        loanTable.addCell("Senior Manager ID:");
	        loanTable.addCell(loanApplicationExtended.getLoanApplication().getSeniorManagerId().toString());

	        document.add(loanTable);

	        // Loan Approvals Section
	        document.add(new Paragraph("Loan Approvals", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
	        document.add(new Chunk(" "));
	        
	        for (LoanApprovalDTO loanApproval : loanApplicationExtended.getLoanApprovals()) {
	            // Approval Details
	            document.add(new Paragraph("Approval Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
	            PdfPTable approvalTable = new PdfPTable(2);
	            approvalTable.setWidthPercentage(100); 
	            approvalTable.setSpacingBefore(10f);
	            approvalTable.setSpacingAfter(10f);
	            
	            approvalTable.addCell("Approval ID:");
	            approvalTable.addCell(loanApproval.getApprovalId().toString());
	            approvalTable.addCell("Loan ID:");
	            approvalTable.addCell(loanApproval.getLoanId().toString());
	            approvalTable.addCell("Approver ID:");
	            approvalTable.addCell(loanApproval.getApproverId().toString());
	            approvalTable.addCell("Approver Name:");
	            approvalTable.addCell(userService.getNameByUserId(loanApproval.getApproverId()));
	            approvalTable.addCell("Approval Level:");
	            approvalTable.addCell(getApprovalLevelString(loanApproval.getApprovalLevel()));
	            approvalTable.addCell("Approval Status:");
	            approvalTable.addCell(loanApproval.getApprovalStatus());
	            approvalTable.addCell("Remarks:");
	            approvalTable.addCell(loanApproval.getRemarks());
	            approvalTable.addCell("Approval Date:");
	            approvalTable.addCell(loanApproval.getApprovalDate().toString());
	            approvalTable.addCell("SLA:");
	            approvalTable.addCell(loanApproval.getSLA().toString());
	            
	            document.add(approvalTable);

	            // Specific Assessments
	            addAssessmentDetails(document, loanApplicationExtended, loanApproval);
	        }
	        
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    } finally {
	        document.close();
	    }

	    return byteArrayOutputStream.toByteArray();
	}

	private String getApprovalLevelString(int approvalLevel) {
	    switch (approvalLevel) {
	        case 1: return "Underwriter";
	        case 2: return "Risk Analyst";
	        case 3: return "Compliance Officer";
	        case 4: return "Manager";
	        case 5: return "Senior Manager";
	        default: return "Unknown";
	    }
	}

	private void addAssessmentDetails(Document document, LoanApplicationExtended loanApplicationExtended, LoanApprovalDTO loanApproval) throws DocumentException {
	    if (loanApproval.getApprovalLevel() == 1) {
	        document.add(new Paragraph("Underwriter Assessment", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
	        PdfPTable assessmentTable = new PdfPTable(2);
	        assessmentTable.setWidthPercentage(100);
	        assessmentTable.setSpacingBefore(10f);
	        assessmentTable.setSpacingAfter(10f);

	        assessmentTable.addCell("Underwriter Assessment ID:");
	        assessmentTable.addCell(loanApplicationExtended.getUnderwriterAssessment().getUnderwriterAssessmentId().toString());
	        assessmentTable.addCell("Loan To Value Ratio:");
	        assessmentTable.addCell(loanApplicationExtended.getUnderwriterAssessment().getLoanToValueRatio().toString());
	        assessmentTable.addCell("Income Verification Status:");
	        assessmentTable.addCell(loanApplicationExtended.getUnderwriterAssessment().getIncomeVerificationStatus());
	        assessmentTable.addCell("Assessment Outcome:");
	        assessmentTable.addCell(loanApplicationExtended.getUnderwriterAssessment().getAssessmentOutcome());
	        assessmentTable.addCell("Remarks:");
	        assessmentTable.addCell(loanApplicationExtended.getUnderwriterAssessment().getRemarks());
	        assessmentTable.addCell("Assessment Date:");
	        assessmentTable.addCell(loanApplicationExtended.getUnderwriterAssessment().getAssessmentDate().toString());

	        document.add(assessmentTable);
	    } else if (loanApproval.getApprovalLevel() == 2) {
	        document.add(new Paragraph("Risk Assessment", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
	        PdfPTable assessmentTable = new PdfPTable(2);
	        assessmentTable.setWidthPercentage(100);
	        assessmentTable.setSpacingBefore(10f);
	        assessmentTable.setSpacingAfter(10f);

	        assessmentTable.addCell("Assessment ID:");
	        assessmentTable.addCell(loanApplicationExtended.getRiskAssessment().getAssessmentId().toString());
	        assessmentTable.addCell("Debt To Income Ratio:");
	        assessmentTable.addCell(loanApplicationExtended.getRiskAssessment().getDebtToIncomeRatio().toString());
	        assessmentTable.addCell("Credit Score:");
	        assessmentTable.addCell(loanApplicationExtended.getRiskAssessment().getCreditScore().toString());
	        assessmentTable.addCell("Risk Category:");
	        assessmentTable.addCell(loanApplicationExtended.getRiskAssessment().getRiskCategory());
	        assessmentTable.addCell("Remarks:");
	        assessmentTable.addCell(loanApplicationExtended.getRiskAssessment().getRemarks());
	        assessmentTable.addCell("Assessment Date:");
	        assessmentTable.addCell(loanApplicationExtended.getRiskAssessment().getAssessmentDate().toString());

	        document.add(assessmentTable);
	    } else if (loanApproval.getApprovalLevel() == 3) {
	        document.add(new Paragraph("Compliance Assessment", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
	        PdfPTable assessmentTable = new PdfPTable(2);
	        assessmentTable.setWidthPercentage(100);
	        assessmentTable.setSpacingBefore(10f);
	        assessmentTable.setSpacingAfter(10f);

	        assessmentTable.addCell("Compliance ID:");
	        assessmentTable.addCell(loanApplicationExtended.getComplianceAssessment().getComplianceId().toString());
	        assessmentTable.addCell("Compliance Status:");
	        assessmentTable.addCell(loanApplicationExtended.getComplianceAssessment().getComplianceStatus());
	        assessmentTable.addCell("Compliance Remarks:");
	        assessmentTable.addCell(loanApplicationExtended.getComplianceAssessment().getRemarks());
	        assessmentTable.addCell("Compliance Assessment Date:");
	        assessmentTable.addCell(loanApplicationExtended.getComplianceAssessment().getAssessmentDate().toString());

	        document.add(assessmentTable);
	    }
	}
}