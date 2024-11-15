package com.freddiemac.loanacquisition.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freddiemac.loanacquisition.dto.LenderDTO;
import com.freddiemac.loanacquisition.entity.Lender;
import com.freddiemac.loanacquisition.repository.LenderRepository;

@Service
public class LenderService {
	
	@Autowired
	private LenderRepository lenderRepository;

	private LenderDTO convertToDTO(Lender lender) {
		LenderDTO lenderDto = new LenderDTO();
		lenderDto.setLenderId(lender.getLenderId());
		lenderDto.setAddress(lender.getAddress());
		lenderDto.setWebisteUrl(lender.getwebsiteUrl());
		lenderDto.setDateJoined(lender.getDateJoined());
		lenderDto.setEmail(lender.getEmail());
		lenderDto.setIsActive(lender.getIsActive());
		lenderDto.setLenderName(lender.getLenderName());
		lenderDto.setLenderType(lender.getLenderType().name());
		lenderDto.setRegistrationNumber(lender.getRegistrationNumber());
		lenderDto.setRiskScore(lender.getRiskScore());
		return lenderDto;
	}
	
	public List<LenderDTO> getAllLenders() {
		return lenderRepository.findAll().stream().map(this::convertToDTO).toList();
	}
	
	public LenderDTO getLenderById(UUID lenderId) {
		return lenderRepository.findById(lenderId).map(this::convertToDTO).orElse(null);
	}
}
