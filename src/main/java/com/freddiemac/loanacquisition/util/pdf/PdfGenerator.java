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

    private final UserService userService;
    
    @Autowired
    public PdfGenerator(UserService userService) {
        this.userService = userService;
    }

    // Helper method to safely handle null values
    private String nullSafe(Object value) {
        return value != null ? value.toString() : "N/A";
    }

    public byte[] generateLoanApplicationPdf(LoanApplicationExtended loanApplicationExtended) {
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Title
            document.addTitle("Loan Acquisition Report: " + nullSafe(loanApplicationExtended.getLoanApplication().getLoanId()));
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
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getLoanId()));
            loanTable.addCell("Loan Amount:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getLoanAmount()));
            loanTable.addCell("Loan Type:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getLoanType()));
            loanTable.addCell("Application Status:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getApplicationStatus()));
            loanTable.addCell("Risk Level:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getRiskLevel()));
            loanTable.addCell("Created At:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getCreatedAt()));
            loanTable.addCell("Updated At:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getUpdatedAt()));
            loanTable.addCell("Required Approval Matrix:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getRequiredApprovalMatrix()));
            loanTable.addCell("Final Approval Status:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getFinalApprovalStatus()));
            loanTable.addCell("Final Approval Timestamp:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getFinalApprovalTimestamp()));
            loanTable.addCell("Is Active:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getIsActive()));
            loanTable.addCell("Underwriter ID:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getUnderwriterId()));
            loanTable.addCell("Risk Analyst ID:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getRiskAnalystId()));
            loanTable.addCell("Compliance Officer ID:");
            loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getComplianceOfficerId()));

            if(loanApplicationExtended.getLoanApplication().getRequiredApprovalMatrix() != 3) {
                loanTable.addCell("Manager ID:");
                loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getManagerId()));
            }

            if(loanApplicationExtended.getLoanApplication().getRequiredApprovalMatrix() == 5) {
                loanTable.addCell("Senior Manager ID:");
                loanTable.addCell(nullSafe(loanApplicationExtended.getLoanApplication().getSeniorManagerId()));
            }

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
                approvalTable.addCell(nullSafe(loanApproval.getApprovalId()));
                approvalTable.addCell("Loan ID:");
                approvalTable.addCell(nullSafe(loanApproval.getLoanId()));
                approvalTable.addCell("Approver ID:");
                approvalTable.addCell(nullSafe(loanApproval.getApproverId()));
                approvalTable.addCell("Approver Name:");
                approvalTable.addCell(nullSafe(userService.getNameByUserId(loanApproval.getApproverId())));
                approvalTable.addCell("Approval Level:");
                approvalTable.addCell(nullSafe(getApprovalLevelString(loanApproval.getApprovalLevel())));
                approvalTable.addCell("Approval Status:");
                approvalTable.addCell(nullSafe(loanApproval.getApprovalStatus()));
                approvalTable.addCell("Remarks:");
                approvalTable.addCell(nullSafe(loanApproval.getRemarks()));
                approvalTable.addCell("Approval Date:");
                approvalTable.addCell(nullSafe(loanApproval.getApprovalDate()));
                approvalTable.addCell("SLA:");
                approvalTable.addCell(nullSafe(loanApproval.getSLA()));

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
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getUnderwriterAssessment().getUnderwriterAssessmentId()));
            assessmentTable.addCell("Loan To Value Ratio:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getUnderwriterAssessment().getLoanToValueRatio()));
            assessmentTable.addCell("Income Verification Status:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getUnderwriterAssessment().getIncomeVerificationStatus()));
            assessmentTable.addCell("Assessment Outcome:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getUnderwriterAssessment().getAssessmentOutcome()));
            assessmentTable.addCell("Remarks:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getUnderwriterAssessment().getRemarks()));
            assessmentTable.addCell("Assessment Date:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getUnderwriterAssessment().getAssessmentDate()));

            document.add(assessmentTable);
        } else if (loanApproval.getApprovalLevel() == 2) {
            document.add(new Paragraph("Risk Assessment", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            PdfPTable assessmentTable = new PdfPTable(2);
            assessmentTable.setWidthPercentage(100);
            assessmentTable.setSpacingBefore(10f);
            assessmentTable.setSpacingAfter(10f);

            assessmentTable.addCell("Assessment ID:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getRiskAssessment().getAssessmentId()));
            assessmentTable.addCell("Debt To Income Ratio:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getRiskAssessment().getDebtToIncomeRatio()));
            assessmentTable.addCell("Credit Score:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getRiskAssessment().getCreditScore()));
            assessmentTable.addCell("Risk Category:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getRiskAssessment().getRiskCategory()));
            assessmentTable.addCell("Remarks:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getRiskAssessment().getRemarks()));
            assessmentTable.addCell("Assessment Date:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getRiskAssessment().getAssessmentDate()));

            document.add(assessmentTable);
        } else if (loanApproval.getApprovalLevel() == 3) {
            document.add(new Paragraph("Compliance Assessment", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            PdfPTable assessmentTable = new PdfPTable(2);
            assessmentTable.setWidthPercentage(100);
            assessmentTable.setSpacingBefore(10f);
            assessmentTable.setSpacingAfter(10f);

            assessmentTable.addCell("Compliance ID:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getComplianceAssessment().getComplianceId()));
            assessmentTable.addCell("Compliance Status:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getComplianceAssessment().getComplianceStatus()));
            assessmentTable.addCell("Compliance Remarks:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getComplianceAssessment().getRemarks()));
            assessmentTable.addCell("Compliance Assessment Date:");
            assessmentTable.addCell(nullSafe(loanApplicationExtended.getComplianceAssessment().getAssessmentDate()));

            document.add(assessmentTable);
        }
    }
}
