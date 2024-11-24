package com.freddiemac.loanacquisition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freddiemac.loanacquisition.dto.DashboardMetrics;
import com.freddiemac.loanacquisition.service.LoanApplicationService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RolesAllowed("SENIOR_MANAGER")
@RequestMapping("/api/senior-manager")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class SeniorManagerController {

	@Autowired
	private LoanApplicationService loanApplicationService;

	@GetMapping("/dashboard_metrics")
	public ResponseEntity<DashboardMetrics> getDashboardMetrics() {
		return ResponseEntity.ok(loanApplicationService.getDashboardMetrics());
	}
}
