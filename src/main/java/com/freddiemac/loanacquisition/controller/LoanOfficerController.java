package com.freddiemac.loanacquisition.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.freddiemac.loanacquisition.dto.ComplianceAssessmentDTO;
import com.freddiemac.loanacquisition.dto.LenderDTO;
import com.freddiemac.loanacquisition.dto.LoanApplicationDTO;
import com.freddiemac.loanacquisition.dto.LoanApplicationExtended;
import com.freddiemac.loanacquisition.dto.LoanApprovalDTO;
import com.freddiemac.loanacquisition.dto.LoanDocumentDTO;
import com.freddiemac.loanacquisition.dto.LoanOfficerDashboardMetrics;
import com.freddiemac.loanacquisition.dto.NotificationDTO;
import com.freddiemac.loanacquisition.dto.RiskAssessmentDTO;
import com.freddiemac.loanacquisition.dto.UnderwriterAssessmentDTO;
import com.freddiemac.loanacquisition.entity.DocumentType;
import com.freddiemac.loanacquisition.entity.RiskLevel;
import com.freddiemac.loanacquisition.security.ApiResponse;
import com.freddiemac.loanacquisition.service.ComplianceAssessmentService;
import com.freddiemac.loanacquisition.service.LenderService;
import com.freddiemac.loanacquisition.service.LoanApplicationService;
import com.freddiemac.loanacquisition.service.LoanApprovalService;
import com.freddiemac.loanacquisition.service.NotificationService;
import com.freddiemac.loanacquisition.service.RiskAssessmentService;
import com.freddiemac.loanacquisition.service.UnderwriterAssessmentService;
import com.freddiemac.loanacquisition.service.UserService;
import com.freddiemac.loanacquisition.util.pdf.PdfGenerator;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RolesAllowed("LOAN_OFFICER")
@RequestMapping("/api/loan-officer")
public class LoanOfficerController {

	@Autowired
	private LoanApplicationService loanApplicationService;
	
	@Autowired
	private LoanApprovalService loanApprovalService;
	
	@Autowired
	private UnderwriterAssessmentService underwriterAssessmentService;
	
	@Autowired
	private RiskAssessmentService riskAssessmentService;
	
	@Autowired
	private ComplianceAssessmentService complianceAssessmentService;
	
	@Autowired
	private LenderService lenderService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/new_application")
	public ResponseEntity<ApiResponse> addNewLoanApplication(
			@RequestParam String lenderId,
	        @RequestParam String loanAmount,
	        @RequestParam String loanType,
	        @RequestParam String riskLevel,
	        @RequestParam String isActive,
	        @RequestParam String underwriterId,
	        @RequestParam String riskAnalystId,
	        @RequestParam String complianceOfficerId,
	        @RequestParam String managerId,
	        @RequestParam String seniorManagerId,
	        @RequestParam MultipartFile loanApplicationForm,
	        @RequestParam MultipartFile loanAgreementDocument,
	        @RequestParam MultipartFile incomeVerificationDocuments,
	        @RequestParam MultipartFile complianceRegulatoryDocuments,
	        @RequestParam MultipartFile collateralDocuments) {
	    
	    
	    
	    LoanApplicationDTO loanApplication = new LoanApplicationDTO();
	    loanApplication.setLenderId(UUID.fromString(lenderId));
	    loanApplication.setLoanAmount(new BigDecimal(loanAmount));
	    loanApplication.setLoanType(loanType);
	    loanApplication.setRiskLevel(RiskLevel.valueOf(riskLevel.toUpperCase()));
	    loanApplication.setIsActive(isActive.equals("true"));
	    loanApplication.setUnderwriterId(UUID.fromString(underwriterId));
	    loanApplication.setRiskAnalystId(UUID.fromString(riskAnalystId));
	    loanApplication.setComplianceOfficerId(UUID.fromString(complianceOfficerId));
	    if(managerId!=null && !managerId.isBlank())
	    	loanApplication.setManagerId(UUID.fromString(managerId));
	    if(seniorManagerId!=null && !seniorManagerId.isBlank())
	    	loanApplication.setSeniorManagerId(UUID.fromString(seniorManagerId));
	    
	    List<LoanDocumentDTO> loanDocuments = new ArrayList<LoanDocumentDTO>();
	    try {
	    	
	    	LoanDocumentDTO loanApplicationFormDoc = new LoanDocumentDTO();
	 	    loanApplicationFormDoc.setDocumentName(loanApplicationForm.getName() + ".pdf");
	 	    loanApplicationFormDoc.setDocumentType(DocumentType.LOAN_REQUEST.toString());
			loanApplicationFormDoc.setFileContent(loanApplicationForm.getBytes());
			loanApplicationFormDoc.setUploadedAt(new Timestamp(System.currentTimeMillis()));
			
			LoanDocumentDTO loanAgreementDocumentDoc = new LoanDocumentDTO();
			loanAgreementDocumentDoc.setDocumentName(loanAgreementDocument.getName() + ".pdf");
			loanAgreementDocumentDoc.setDocumentType(DocumentType.LOAN_AGREEMENT.toString());
			loanAgreementDocumentDoc.setFileContent(loanAgreementDocument.getBytes());
			loanAgreementDocumentDoc.setUploadedAt(new Timestamp(System.currentTimeMillis()));
			
			LoanDocumentDTO incomeVerificationDocumentsDoc = new LoanDocumentDTO();
			incomeVerificationDocumentsDoc.setDocumentName(incomeVerificationDocuments.getName() + ".pdf");
			incomeVerificationDocumentsDoc.setDocumentType(DocumentType.INCOME_VERIFICATION.toString());
			incomeVerificationDocumentsDoc.setFileContent(incomeVerificationDocuments.getBytes());
			incomeVerificationDocumentsDoc.setUploadedAt(new Timestamp(System.currentTimeMillis()));
			
			LoanDocumentDTO complianceRegulatoryDocumentsDoc = new LoanDocumentDTO();
			complianceRegulatoryDocumentsDoc.setDocumentName(complianceRegulatoryDocuments.getName() + ".pdf");
			complianceRegulatoryDocumentsDoc.setDocumentType(DocumentType.COMPLIANCE_REGULATORY.toString());
			complianceRegulatoryDocumentsDoc.setFileContent(complianceRegulatoryDocuments.getBytes());
			complianceRegulatoryDocumentsDoc.setUploadedAt(new Timestamp(System.currentTimeMillis()));
			
			LoanDocumentDTO collateralDocumentsDoc = new LoanDocumentDTO();
			collateralDocumentsDoc.setDocumentName(collateralDocuments.getName() + ".pdf");
			collateralDocumentsDoc.setDocumentType(DocumentType.COLLATERAL.toString());
			collateralDocumentsDoc.setFileContent(collateralDocuments.getBytes());
			collateralDocumentsDoc.setUploadedAt(new Timestamp(System.currentTimeMillis()));
			
			loanDocuments.add(loanApplicationFormDoc);
			loanDocuments.add(loanAgreementDocumentDoc);
			loanDocuments.add(incomeVerificationDocumentsDoc);
			loanDocuments.add(complianceRegulatoryDocumentsDoc);
			loanDocuments.add(collateralDocumentsDoc);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    loanApplication.setLoanDocuments(loanDocuments);
	    
	    LoanApplicationDTO newLoanApplication = loanApplicationService.createLoanApplication(loanApplication);
	    if (newLoanApplication.getLoanId() != null)
	        return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body(new ApiResponse(true, "Loan Acquisition Application Created !!"));
	    
	    return ResponseEntity
	            .badRequest()
	            .body(new ApiResponse(false, "Loan Application is missing important fields !"));
	}

	
	@PostMapping("/edit_application")
	public ResponseEntity<ApiResponse> editLoanApplication(
			@RequestParam String loanId,
			@RequestParam String lenderId,
	        @RequestParam String loanAmount,
	        @RequestParam String loanType,
	        @RequestParam String riskLevel,
	        @RequestParam String isActive,
	        @RequestParam String underwriterId,
	        @RequestParam String riskAnalystId,
	        @RequestParam String complianceOfficerId,
	        @RequestParam String managerId,
	        @RequestParam String seniorManagerId,
	        @RequestParam(required = false) MultipartFile loanApplicationForm,
	        @RequestParam(required = false) MultipartFile loanAgreementDocument,
	        @RequestParam(required = false) MultipartFile incomeVerificationDocuments,
	        @RequestParam(required = false) MultipartFile complianceRegulatoryDocuments,
	        @RequestParam(required = false) MultipartFile collateralDocuments) {
		
		LoanApplicationDTO loanApplication = new LoanApplicationDTO();
		loanApplication.setLoanId(UUID.fromString(loanId));
	    loanApplication.setLenderId(UUID.fromString(lenderId));
	    loanApplication.setLoanAmount(new BigDecimal(loanAmount));
	    loanApplication.setLoanType(loanType);
	    loanApplication.setRiskLevel(RiskLevel.valueOf(riskLevel.toUpperCase()));
	    loanApplication.setIsActive(isActive.equals("true"));
	    loanApplication.setUnderwriterId(UUID.fromString(underwriterId));
	    loanApplication.setRiskAnalystId(UUID.fromString(riskAnalystId));
	    loanApplication.setComplianceOfficerId(UUID.fromString(complianceOfficerId));
	    if(managerId!=null && !managerId.isBlank() && !managerId.equals("null"))
	    	loanApplication.setManagerId(UUID.fromString(managerId));
	    if(seniorManagerId!=null && !seniorManagerId.isBlank() && !seniorManagerId.equals("null"))
	    	loanApplication.setSeniorManagerId(UUID.fromString(seniorManagerId));
	    
	    List<LoanDocumentDTO> loanDocuments = new ArrayList<LoanDocumentDTO>();
	    try {
	    	if(loanApplicationForm!=null) {
	    		LoanDocumentDTO loanApplicationFormDoc = new LoanDocumentDTO();
		 	    loanApplicationFormDoc.setDocumentName(loanApplicationForm.getName() + ".pdf");
		 	    loanApplicationFormDoc.setDocumentType(DocumentType.LOAN_REQUEST.toString());
				loanApplicationFormDoc.setFileContent(loanApplicationForm.getBytes());
				loanApplicationFormDoc.setUploadedAt(new Timestamp(System.currentTimeMillis()));
				loanDocuments.add(loanApplicationFormDoc);
    		}
	    	
	    	if(loanAgreementDocument!=null) {
	    		LoanDocumentDTO loanAgreementDocumentDoc = new LoanDocumentDTO();
				loanAgreementDocumentDoc.setDocumentName(loanAgreementDocument.getName() + ".pdf");
				loanAgreementDocumentDoc.setDocumentType(DocumentType.LOAN_AGREEMENT.toString());
				loanAgreementDocumentDoc.setFileContent(loanAgreementDocument.getBytes());
				loanAgreementDocumentDoc.setUploadedAt(new Timestamp(System.currentTimeMillis()));
				loanDocuments.add(loanAgreementDocumentDoc);
	    	}
			
			if(incomeVerificationDocuments!=null) {
				LoanDocumentDTO incomeVerificationDocumentsDoc = new LoanDocumentDTO();
				incomeVerificationDocumentsDoc.setDocumentName(incomeVerificationDocuments.getName() + ".pdf");
				incomeVerificationDocumentsDoc.setDocumentType(DocumentType.INCOME_VERIFICATION.toString());
				incomeVerificationDocumentsDoc.setFileContent(incomeVerificationDocuments.getBytes());
				incomeVerificationDocumentsDoc.setUploadedAt(new Timestamp(System.currentTimeMillis()));
				loanDocuments.add(incomeVerificationDocumentsDoc);
			}
			
			if(complianceRegulatoryDocuments!=null) {
				LoanDocumentDTO complianceRegulatoryDocumentsDoc = new LoanDocumentDTO();
				complianceRegulatoryDocumentsDoc.setDocumentName(complianceRegulatoryDocuments.getName() + ".pdf");
				complianceRegulatoryDocumentsDoc.setDocumentType(DocumentType.COMPLIANCE_REGULATORY.toString());
				complianceRegulatoryDocumentsDoc.setFileContent(complianceRegulatoryDocuments.getBytes());
				complianceRegulatoryDocumentsDoc.setUploadedAt(new Timestamp(System.currentTimeMillis()));
				loanDocuments.add(complianceRegulatoryDocumentsDoc);
			}
			
			if(collateralDocuments!=null) {
				LoanDocumentDTO collateralDocumentsDoc = new LoanDocumentDTO();
				collateralDocumentsDoc.setDocumentName(collateralDocuments.getName() + ".pdf");
				collateralDocumentsDoc.setDocumentType(DocumentType.COLLATERAL.toString());
				collateralDocumentsDoc.setFileContent(collateralDocuments.getBytes());
				collateralDocumentsDoc.setUploadedAt(new Timestamp(System.currentTimeMillis()));
				loanDocuments.add(collateralDocumentsDoc);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    loanApplication.setLoanDocuments(loanDocuments);
	    
		LoanApplicationDTO updatedLoanApplication = loanApplicationService.updateLoanApplication(loanApplication);
		if(updatedLoanApplication.getLoanId()!=null)
			return ResponseEntity
	                .status(HttpStatus.ACCEPTED)
	                .body(new ApiResponse(true, "Loan Acquisition Application Updated !!"));
			return ResponseEntity
	                .badRequest()
	                .body(new ApiResponse(false, "Loan Application is missing important fields !"));
	}
	
	@GetMapping("/all_applications")
	public ResponseEntity<?> getAllLoanApplications() {
		List<LoanApplicationDTO> loanApplications =loanApplicationService.getAllLoanApplications();
		return new ResponseEntity<>(loanApplications, HttpStatus.OK);
	}
	
	@GetMapping("/recent_applications")
	public ResponseEntity<?> getRecentLoanApplications() {
		List<LoanApplicationDTO> loanApplications =loanApplicationService.getRecentLoanApplications();
		return new ResponseEntity<>(loanApplications, HttpStatus.OK);
	}
	
	@GetMapping("/pending_final_approval_applications")
	public ResponseEntity<?> getPendingFinalApprovalApplications() {
		List<LoanApplicationDTO> loanApplications = loanApplicationService.getFinalApprovalPendingLoanApplications();
		return new ResponseEntity<>(loanApplications, HttpStatus.OK);
	}
	
	@GetMapping("/completed_final_approval_applications")
	public ResponseEntity<?> getCompletedFinalApprovalApplications() {
		List<LoanApplicationDTO> loanApplications = loanApplicationService.getFinalApprovalCompletedLoanApplications();
		return new ResponseEntity<>(loanApplications, HttpStatus.OK);
	}
	
	@GetMapping("/all_inactive_applications")
	public ResponseEntity<?> getAllInactiveApplications() {
		List<LoanApplicationDTO> loanApplications = loanApplicationService.getAllInactiveApplications();
		return new ResponseEntity<>(loanApplications, HttpStatus.OK);
	}
	
	@GetMapping("/loan_application/{loanId}")
	public ResponseEntity<LoanApplicationDTO> getLoanApplication(@PathVariable UUID loanId) {
		LoanApplicationDTO loanApplication = loanApplicationService.getLoanApplicationById(loanId);
		if(loanApplication!=null)
			return new ResponseEntity<>(loanApplication, HttpStatus.OK);
		else
			return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/loan_application_extended/{loanId}")
	public ResponseEntity<?> getLoanApplicationExtended(@PathVariable UUID loanId) {
		LoanApplicationDTO loanApplication = loanApplicationService.getLoanApplicationById(loanId);
		List<LoanApprovalDTO> loanApprovals = loanApprovalService.getLoanApprovalByLoan_id(loanId);
		UnderwriterAssessmentDTO underwriterAssessment = underwriterAssessmentService.getUnderwriterAssessmentByLoanId(loanId);
		RiskAssessmentDTO riskAssessment  = riskAssessmentService.getRiskAssessmentByLoanId(loanId);
		ComplianceAssessmentDTO complianceAssessment = complianceAssessmentService.getComplianceAssessmentByLoanId(loanId);
		LenderDTO lenderDetails = lenderService.getLenderById(loanApplication.getLenderId());
		LoanApplicationExtended loanApplicationExtended = new LoanApplicationExtended(loanApplication, lenderDetails, loanApprovals,
				underwriterAssessment, riskAssessment, complianceAssessment);
		
		return new ResponseEntity<>(loanApplicationExtended, HttpStatus.OK);
	}
	
	@PutMapping("/loan_application/{loanId}/final_approval/{status}")
	public ResponseEntity<ApiResponse> submitFinalApproval(@PathVariable UUID loanId,@PathVariable String status) {
		LoanApplicationDTO loanApplication = loanApplicationService.updateFinalApprovalStatus(loanId, status.equals("approve"));
		if(loanApplication!=null)
			return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ApiResponse(true, "Loan Acquisition Application Updated !!"));
		else
			return ResponseEntity
	                .badRequest()
	                .body(new ApiResponse(false, "Loan Application not found !"));
	}
	
	@GetMapping("/loan_application/{loanId}/generate_report")
	public ResponseEntity<byte[]> generateLoanAcquisitionReport(@PathVariable UUID loanId) {
		LoanApplicationDTO loanApplication = loanApplicationService.getLoanApplicationById(loanId);
		List<LoanApprovalDTO> loanApprovals = loanApprovalService.getLoanApprovalByLoan_id(loanId);
		UnderwriterAssessmentDTO underwriterAssessment = underwriterAssessmentService.getUnderwriterAssessmentByLoanId(loanId);
		RiskAssessmentDTO riskAssessment  = riskAssessmentService.getRiskAssessmentByLoanId(loanId);
		ComplianceAssessmentDTO complianceAssessment = complianceAssessmentService.getComplianceAssessmentByLoanId(loanId);
		LenderDTO lenderDetails = lenderService.getLenderById(loanApplication.getLenderId());
		LoanApplicationExtended loanApplicationExtended = new LoanApplicationExtended(loanApplication, lenderDetails, loanApprovals,
				underwriterAssessment, riskAssessment, complianceAssessment);
		
		PdfGenerator pdfGenerator = new PdfGenerator(userService);
		byte[] fileContent = pdfGenerator.generateLoanApplicationPdf(loanApplicationExtended);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentDisposition(ContentDisposition.attachment().filename("Loan_Acquisition_Report_" + loanId + ".pdf").build());
	    headers.setContentLength(fileContent.length);

	    return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
	}
	
	@PostMapping("/add_notification")
	public ResponseEntity<?> createNewNotification(@RequestBody NotificationDTO notification) {
		NotificationDTO newNotification = notificationService.createNotification(notification);
		if(newNotification.getNotificationId()!=null)
			return ResponseEntity.ok().build();
		else
			return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/dashboard_metrics")
	public ResponseEntity<LoanOfficerDashboardMetrics> getLoanOfficerDashboardMetrics() {
		return ResponseEntity.ok(loanApplicationService.getLoanOfficerMetrics());
	}
	
}