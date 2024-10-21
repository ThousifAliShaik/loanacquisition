package com.freddiemac.loanacquisition.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freddiemac.loanacquisition.dto.LoanApplicationDTO;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RolesAllowed("LOAN_OFFICER")
@RequestMapping("/api/loan-officer")
public class LoanOfficerController {

	@PostMapping("/new_application")
	public ResponseEntity<String> addNewLoanApplication(@Valid @RequestBody LoanApplicationDTO loanApplication) {
		return null;
	}
	
}