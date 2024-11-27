package com.freddiemac.loanacquisition.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freddiemac.loanacquisition.dto.LoanDocumentDTO;
import com.freddiemac.loanacquisition.service.LoanDocumentService;

@RestController
@RequestMapping("/api/documents")
public class LoanDocumentController {

	@Autowired
	private LoanDocumentService loanDocumentService;
	
	@GetMapping("/loan/{loanId}")
	public List<LoanDocumentDTO> getAllLoanDocuments(@PathVariable String loanId) {
		return loanDocumentService.getLoanDocumentsByLoanId(UUID.fromString(loanId));
	}
	
	@GetMapping("/document/{documentId}")
	public LoanDocumentDTO getDocument(@PathVariable String documentId) {
		return loanDocumentService.getLoanDocumentByDocumentId(UUID.fromString(documentId));
	}
}
