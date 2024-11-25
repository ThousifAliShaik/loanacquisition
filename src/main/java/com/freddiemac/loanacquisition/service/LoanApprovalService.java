package com.freddiemac.loanacquisition.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freddiemac.loanacquisition.dto.LoanApprovalDTO;
import com.freddiemac.loanacquisition.entity.ApplicationStatus;
import com.freddiemac.loanacquisition.entity.ApprovalStatus;
import com.freddiemac.loanacquisition.entity.LoanApplication;
import com.freddiemac.loanacquisition.entity.LoanApproval;
import com.freddiemac.loanacquisition.entity.Notification;
import com.freddiemac.loanacquisition.entity.NotificationType;
import com.freddiemac.loanacquisition.entity.RiskLevel;
import com.freddiemac.loanacquisition.entity.UserRole;
import com.freddiemac.loanacquisition.repository.LoanApplicationRepository;
import com.freddiemac.loanacquisition.repository.LoanApprovalRepository;
import com.freddiemac.loanacquisition.repository.NotificationRepository;
import com.freddiemac.loanacquisition.security.UserPrincipal;
import com.freddiemac.loanacquisition.security.UserRepository;


@Service
public class LoanApprovalService {

    private final LoanApprovalRepository loanApprovalRepository;
    
    private final LoanApplicationRepository loanApplicationRepository;
    
	private final NotificationRepository notificationRepository;
	
	private final UserRepository userRepository;

    public LoanApprovalService(LoanApprovalRepository loanApprovalRepository,
    		LoanApplicationRepository loanApplicationRepository, NotificationRepository notificationRepository,
    		UserRepository userRepository) {
        this.loanApprovalRepository = loanApprovalRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // Convert LoanApproval entity to LoanApprovalDTO
    private LoanApprovalDTO convertToDTO(LoanApproval loanApproval) {
        return new LoanApprovalDTO(
            loanApproval.getApprovalId(),
            loanApproval.getApprover().getUserId(),
            loanApproval.getLoan().getLoanId(),
            loanApproval.getApprover().getUserProfile().getFullName(),
            loanApproval.getApprover().getUserProfile().getRole().getRoleName().name(),
            loanApproval.getApprovalLevel(),
            loanApproval.getApprovalStatus().name(),
            loanApproval.getRemarks(),
            loanApproval.getApprovalDate(),
            loanApproval.getSLA()
        );
    }

    // Convert LoanApprovalDTO to LoanApproval entity
    private LoanApproval convertToEntity(LoanApprovalDTO loanApprovalDTO) {
        LoanApproval loanApproval = new LoanApproval();
        loanApproval.setApprovalId(loanApprovalDTO.getApprovalId());
        loanApproval.setApprovalLevel(loanApprovalDTO.getApprovalLevel());
        loanApproval.setApprovalStatus(ApprovalStatus.valueOf(loanApprovalDTO.getApprovalStatus()));
        loanApproval.setRemarks(loanApprovalDTO.getRemarks());
        loanApproval.setApprovalDate(loanApprovalDTO.getApprovalDate());
        loanApproval.setSLA(loanApprovalDTO.getSLA());
        return loanApproval;
    }

    public List<LoanApprovalDTO> getAllLoanApprovals() {
        return loanApprovalRepository.findAll().stream()
            .map(this::convertToDTO)
            .toList();
    }

    
    public LoanApprovalDTO getLoanApprovalById(UUID approvalId) {
        return loanApprovalRepository.findById(approvalId)
            .map(this::convertToDTO)
            .orElse(null);
    }

    public List<LoanApprovalDTO> getLoanApprovalByLoan_id(UUID loanId) {
        return loanApprovalRepository.findByLoan_LoanId(loanId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }
    
    public List<LoanApprovalDTO> getLoanApprovalByUserid(UUID userId) {
        return loanApprovalRepository.findByApprover_UserId(userId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    public List<LoanApprovalDTO> getPendingLoanApprovalByUserid(UUID userId) {
        return loanApprovalRepository.findByApprover_UserIdAndApprovalStatus(userId, ApprovalStatus.PENDING)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }
    
    public List<LoanApprovalDTO> getCompletedLoanApprovalByUserid(UUID userId) {
        return loanApprovalRepository.findByApprover_UserIdAndApprovalStatusNot(userId, ApprovalStatus.PENDING)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }
    
    public List<LoanApprovalDTO> getPendingForManagerApproval() {
    	UUID userId = UserPrincipal.getCurrentUserId();
    	List<UUID> allPendingApplications =new ArrayList<>(loanApprovalRepository.findByApprover_UserIdAndApprovalStatus(userId, ApprovalStatus.PENDING)
                .stream()
                .map(LoanApproval::getLoan).map(LoanApplication::getLoanId)
                .toList());
    	List<UUID> applicationNotReadyYet = new ArrayList<>(loanApprovalRepository.findByLoan_LoanIdInAndApprovalLevelInAndApprovalStatus(allPendingApplications,
    			List.of(1, 2, 3), ApprovalStatus.PENDING)
    			.stream()
    			.map(LoanApproval::getLoan).map(LoanApplication::getLoanId)
    			.toList()); 
    	
    	allPendingApplications.removeAll(applicationNotReadyYet);

    	return loanApprovalRepository.findByLoan_LoanIdInAndApprover_UserId(allPendingApplications, userId)
    			.stream()
    			.map(this::convertToDTO)
    			.toList();
    }
    
    public List<LoanApprovalDTO> getPendingForSeniorManagerApproval() {
    	UUID userId = UserPrincipal.getCurrentUserId();
    	List<UUID> allPendingApplications =new ArrayList<>(loanApprovalRepository.findByApprover_UserIdAndApprovalStatus(userId, ApprovalStatus.PENDING)
                .stream()
                .map(LoanApproval::getLoan).map(LoanApplication::getLoanId)
                .toList());
    	List<UUID> applicationNotReadyYet = new ArrayList<>(loanApprovalRepository.findByLoan_LoanIdInAndApprovalLevelInAndApprovalStatus(allPendingApplications,
    			List.of(1, 2, 3, 4), ApprovalStatus.PENDING)
    			.stream()
    			.map(LoanApproval::getLoan).map(LoanApplication::getLoanId)
    			.toList()); 
    	
    	allPendingApplications.removeAll(applicationNotReadyYet);

    	return loanApprovalRepository.findByLoan_LoanIdInAndApprover_UserId(allPendingApplications, userId)
    			.stream()
    			.map(this::convertToDTO)
    			.toList();
    }
    
    public LoanApprovalDTO createLoanApproval(LoanApprovalDTO loanApprovalDTO) {
        LoanApproval loanApproval = convertToEntity(loanApprovalDTO);
        LoanApproval savedLoanApproval = loanApprovalRepository.save(loanApproval);
        return convertToDTO(savedLoanApproval);
    }
    
    public LoanApprovalDTO updateLoanApproval(LoanApprovalDTO loanApprovalDTO) {
        LoanApproval loanApproval = loanApprovalRepository.findById(loanApprovalDTO.getApprovalId()).orElseThrow();
        loanApproval.setApprovalStatus(ApprovalStatus.valueOf(loanApprovalDTO.getApprovalStatus()));
        loanApproval.setRemarks(loanApprovalDTO.getRemarks());
        loanApproval.setApprovalDate(new Timestamp(System.currentTimeMillis()));
        LoanApproval savedLoanApproval = loanApprovalRepository.save(loanApproval);
        if((savedLoanApproval.getApprover().getUserProfile().getRole().getRoleName().equals(UserRole.MANAGER)
        		&& savedLoanApproval.getLoan().getRiskLevel().equals(RiskLevel.MEDIUM)) ||
        		(savedLoanApproval.getApprover().getUserProfile().getRole().getRoleName().equals(UserRole.SENIOR_MANAGER)
        		&& savedLoanApproval.getLoan().getRiskLevel().equals(RiskLevel.HIGH))) {
        	LoanApplication loanApplication = loanApplicationRepository.findById(savedLoanApproval.getLoan().getLoanId()).orElse(null);
        	if(loanApplication!=null) {
        		loanApplication.setApplicationStatus(ApplicationStatus.SUBMITTED);
        		loanApplication.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        		createNewAssignmentNotification(loanApplication.getLoanId(), loanApplication.getFinalApprover().getUserId());
        		loanApplicationRepository.saveAndFlush(loanApplication);
        	}
    		
        }
        return convertToDTO(savedLoanApproval);
    }

    
    public void deleteLoanApproval(UUID approvalId) {
        loanApprovalRepository.deleteById(approvalId);
    }
    
    private void createNewAssignmentNotification(UUID loanId, UUID userId) {
    	Notification notification = new Notification();
    	notification.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    	notification.setIsRead(false);
    	notification.setUser(userRepository.findById(userId).orElseThrow());
    	notification.setLoan(loanApplicationRepository.findById(loanId).orElseThrow());
    	notification.setMessage("A new loan acquisition application has been assigned to you.");
    	notification.setNotificationType(NotificationType.LOAN_APPLICATION_UPDATE);
    	notificationRepository.save(notification);
    }
}
