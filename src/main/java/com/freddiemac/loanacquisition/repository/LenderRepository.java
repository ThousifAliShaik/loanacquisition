package com.freddiemac.loanacquisition.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freddiemac.loanacquisition.entity.Lender;

public interface LenderRepository extends JpaRepository<Lender, UUID>{

}
