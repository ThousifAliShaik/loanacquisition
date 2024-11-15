package com.freddiemac.loanacquisition.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freddiemac.loanacquisition.dto.ComplianceAssessmentDTO;
import com.freddiemac.loanacquisition.dto.LenderDTO;
import com.freddiemac.loanacquisition.dto.LoanApplicationDTO;
import com.freddiemac.loanacquisition.dto.LoanApprovalDTO;
import com.freddiemac.loanacquisition.dto.RiskAssessmentDTO;
import com.freddiemac.loanacquisition.dto.RoleDTO;
import com.freddiemac.loanacquisition.dto.UnderwriterAssessmentDTO;
import com.freddiemac.loanacquisition.dto.UserDTO;
import com.freddiemac.loanacquisition.entity.UserRole;
import com.freddiemac.loanacquisition.service.ComplianceAssessmentService;
import com.freddiemac.loanacquisition.service.LenderService;
import com.freddiemac.loanacquisition.service.LoanApplicationService;
import com.freddiemac.loanacquisition.service.LoanApprovalService;
import com.freddiemac.loanacquisition.service.RiskAssessmentService;
import com.freddiemac.loanacquisition.service.RoleService;
import com.freddiemac.loanacquisition.service.UnderwriterAssessmentService;
import com.freddiemac.loanacquisition.service.UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loan_application")
public class LoanApplicationController {

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
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{userId}/pending_assessment")
	@RolesAllowed({"UNDERWRITER", "RISK_ANALYST", "COMPLIANCE_OFFICER"})
	public ResponseEntity<List<LoanApplicationDTO>> getApplicationsPendingAssessment(@PathVariable UUID userId) {
		List<UUID> loanApplicationIds = loanApprovalService.getPendingLoanApprovalByUserid(userId).stream()
				.map(LoanApprovalDTO::getLoanId) 
			    .toList();
		List<LoanApplicationDTO> loanApplications = loanApplicationService.getLoanApplicationsByIds(loanApplicationIds);
		return ResponseEntity.ok(loanApplications);
	}
	
	@GetMapping("/{userId}/completed_assessment")
	@RolesAllowed({"UNDERWRITER", "RISK_ANALYST", "COMPLIANCE_OFFICER"})
	public ResponseEntity<List<LoanApplicationDTO>> getApplicationsCompletedAssessment(@PathVariable UUID userId) {
		List<UUID> loanApplicationIds = loanApprovalService.getCompletedLoanApprovalByUserid(userId).stream()
				.map(LoanApprovalDTO::getLoanId)
			    .toList();
		List<LoanApplicationDTO> loanApplications = loanApplicationService.getLoanApplicationsByIds(loanApplicationIds);
		return ResponseEntity.ok(loanApplications);
	}
	
	@GetMapping("/{userId}/pending_manager_approval")
	@RolesAllowed("MANAGER")
	public ResponseEntity<List<LoanApplicationDTO>> getPendingApplicationsForManager(@PathVariable UUID userId) {
		List<UUID> loanApplicationIds = loanApprovalService.getPendingForManagerApproval(userId).stream()
				.map(LoanApprovalDTO::getLoanId) 
			    .toList();
		List<LoanApplicationDTO> loanApplications = loanApplicationService.getLoanApplicationsByIds(loanApplicationIds);
		return ResponseEntity.ok(loanApplications);
	}
	
	@GetMapping("/{userId}/pending_senior_manager_approval")
	@RolesAllowed("SENIOR_MANAGER")
	public ResponseEntity<List<LoanApplicationDTO>> getPendingApplicationsForSeniorManager(@PathVariable UUID userId) {
		List<UUID> loanApplicationIds = loanApprovalService.getPendingForSeniorManagerApproval(userId).stream()
				.map(LoanApprovalDTO::getLoanId) 
			    .toList();
		List<LoanApplicationDTO> loanApplications = loanApplicationService.getLoanApplicationsByIds(loanApplicationIds);
		return ResponseEntity.ok(loanApplications);
	}
	
	@PostMapping("/submit_underwriter_assessment")
	@RolesAllowed("UNDERWRITER")
	public ResponseEntity<UnderwriterAssessmentDTO> submitUnderwriterAssessment(@Valid @RequestBody UnderwriterAssessmentDTO underwriterAssessment) {
		UnderwriterAssessmentDTO submittedUnderwriterAssessment = underwriterAssessmentService.createUnderwriterAssessment(underwriterAssessment);
		if(submittedUnderwriterAssessment.getUnderwriterAssessmentId()!=null)
			return ResponseEntity.ok(submittedUnderwriterAssessment);
		else
			return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/submit_risk_assessment")
	@RolesAllowed("RISK_ANALYST")
	public ResponseEntity<RiskAssessmentDTO> submitRiskAssessment(@Valid @RequestBody RiskAssessmentDTO riskAssessment) {
		RiskAssessmentDTO submittedRiskAssessment = riskAssessmentService.createRiskAssessment(riskAssessment);
		if(submittedRiskAssessment.getAssessmentId()!=null)
			return ResponseEntity.ok(submittedRiskAssessment);
		else
			return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/submit_compliance_assessment")
	@RolesAllowed("COMPLIANCE_OFFICER")
	public ResponseEntity<ComplianceAssessmentDTO> submitComplianceAssessment(@Valid @RequestBody ComplianceAssessmentDTO complianceAssessment) {
		ComplianceAssessmentDTO submittedComplianceAssessment = complianceAssessmentService.createComplianceAssessment(complianceAssessment);
		if(submittedComplianceAssessment.getComplianceId()!=null)
			return ResponseEntity.ok(submittedComplianceAssessment);
		else
			return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/submit_loan_approval")
	@RolesAllowed({"UNDERWRITER", "RISK_ANALYST", "COMPLIANCE_OFFICER", "MANAGER", "SENIOR_MANAGER"})
	public ResponseEntity<LoanApprovalDTO> submitLoanApproval(@Valid @RequestBody LoanApprovalDTO loanApproval) {
		LoanApprovalDTO submittedLoanApproval = loanApprovalService.updateLoanApproval(loanApproval);
		return ResponseEntity.ok(submittedLoanApproval);
	}
	
	@GetMapping("/all_lenders")
	@RolesAllowed({"UNDERWRITER", "RISK_ANALYST", "COMPLIANCE_OFFICER", "MANAGER", "SENIOR_MANAGER"})
	public ResponseEntity<List<LenderDTO>> getAllLenders() {
		return ResponseEntity.ok(lenderService.getAllLenders());
	}
	
	@GetMapping("/lender/{id}")
	@RolesAllowed({"UNDERWRITER", "RISK_ANALYST", "COMPLIANCE_OFFICER", "MANAGER", "SENIOR_MANAGER"})
	public ResponseEntity<LenderDTO> getLenderDetails(@PathVariable UUID id) {
		return ResponseEntity.ok(lenderService.getLenderById(id));
	}
	
	@GetMapping("/all_compliance_officers")
	@RolesAllowed({"UNDERWRITER", "RISK_ANALYST", "COMPLIANCE_OFFICER", "MANAGER", "SENIOR_MANAGER"})
	public ResponseEntity<List<UserDTO>> getAllComplianceOfficers() {
		RoleDTO role = roleService.getRoleByName(UserRole.COMPLIANCE_OFFICER);
		return ResponseEntity.ok(userService.getByRole(role.getRoleId()));
	}
	
	@GetMapping("/all_underwriters")
	@RolesAllowed({"UNDERWRITER", "RISK_ANALYST", "COMPLIANCE_OFFICER", "MANAGER", "SENIOR_MANAGER"})
	public ResponseEntity<List<UserDTO>> getAllUnderwriters() {
		RoleDTO role = roleService.getRoleByName(UserRole.UNDERWRITER);
		return ResponseEntity.ok(userService.getByRole(role.getRoleId()));
	}
	
	@GetMapping("/all_risk_analysts")
	@RolesAllowed({"UNDERWRITER", "RISK_ANALYST", "COMPLIANCE_OFFICER", "MANAGER", "SENIOR_MANAGER"})
	public ResponseEntity<List<UserDTO>> getAllRiskAnalysts() {
		RoleDTO role = roleService.getRoleByName(UserRole.RISK_ANALYST);
		return ResponseEntity.ok(userService.getByRole(role.getRoleId()));
	}
	
	@GetMapping("/all_managers")
	@RolesAllowed({"UNDERWRITER", "RISK_ANALYST", "COMPLIANCE_OFFICER", "MANAGER", "SENIOR_MANAGER"})
	public ResponseEntity<List<UserDTO>> getAllManagers() {
		RoleDTO role = roleService.getRoleByName(UserRole.MANAGER);
		return ResponseEntity.ok(userService.getByRole(role.getRoleId()));
	}
	
	@GetMapping("/all_senior_managers")
	@RolesAllowed({"UNDERWRITER", "RISK_ANALYST", "COMPLIANCE_OFFICER", "MANAGER", "SENIOR_MANAGER"})
	public ResponseEntity<List<UserDTO>> getAllSeniorManagers() {
		RoleDTO role = roleService.getRoleByName(UserRole.SENIOR_MANAGER);
		return ResponseEntity.ok(userService.getByRole(role.getRoleId()));
	}
}
