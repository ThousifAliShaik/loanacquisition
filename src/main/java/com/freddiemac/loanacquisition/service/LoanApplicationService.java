package com.freddiemac.loanacquisition.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freddiemac.loanacquisition.dto.LoanApplicationDTO;
import com.freddiemac.loanacquisition.dto.LoanOfficerDashboardMetrics;
import com.freddiemac.loanacquisition.entity.ApplicationStatus;
import com.freddiemac.loanacquisition.entity.ApprovalStatus;
import com.freddiemac.loanacquisition.entity.LoanApplication;
import com.freddiemac.loanacquisition.entity.LoanApproval;
import com.freddiemac.loanacquisition.entity.LoanType;
import com.freddiemac.loanacquisition.entity.Notification;
import com.freddiemac.loanacquisition.entity.NotificationType;
import com.freddiemac.loanacquisition.entity.RiskLevel;
import com.freddiemac.loanacquisition.entity.User;
import com.freddiemac.loanacquisition.repository.LenderRepository;
import com.freddiemac.loanacquisition.repository.LoanApplicationRepository;
import com.freddiemac.loanacquisition.repository.LoanApprovalRepository;
import com.freddiemac.loanacquisition.repository.NotificationRepository;
import com.freddiemac.loanacquisition.security.UserPrincipal;
import com.freddiemac.loanacquisition.security.UserRepository;

@Service
public class LoanApplicationService {

	@Autowired
    private LoanApplicationRepository loanApplicationRepository;
    
	@Autowired
    private LoanApprovalRepository loanApprovalRepostiory;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private LenderRepository lenderRepository;

   
    private LoanApplicationDTO convertToDTO(LoanApplication loanApplication) {
        return new LoanApplicationDTO(loanApplication.getLoanId(),
        		 loanApplication.getLender().getLenderId(),
        		 loanApplication.getLoanAmount(),
                 loanApplication.getLoanType().name(),
                 loanApplication.getApplicationStatus().name(),
                 loanApplication.getRiskLevel(),
                 loanApplication.getCreatedAt(),
                 loanApplication.getUpdatedAt(),
                 loanApplication.getRequiredApprovalMatrix(),
                 loanApplication.getFinalApprovalStatus().name(),
                 loanApplication.getFinalApprovalTimestamp(),
                 loanApplication.getIsActive()
        );
    }

    // Convert LoanApplicationDTO to LoanApplication entity
    private LoanApplication convertToEntity(LoanApplicationDTO loanApplicationDTO) {
        LoanApplication loanApplication = new LoanApplication();
        if(loanApplicationDTO.getLoanId()!=null)
        	loanApplication.setLoanId(loanApplicationDTO.getLoanId());
        loanApplication.setLender(lenderRepository.findById(loanApplicationDTO.getLenderId()).orElse(null));
        loanApplication.setLoanAmount(loanApplicationDTO.getLoanAmount());
        loanApplication.setLoanType(LoanType.valueOf(loanApplicationDTO.getLoanType()));
        if(loanApplicationDTO.getApplicationStatus()!=null)
        	loanApplication.setApplicationStatus(ApplicationStatus.valueOf(loanApplicationDTO.getApplicationStatus()));
        loanApplication.setRiskLevel(RiskLevel.valueOf(loanApplicationDTO.getRiskLevel()));
        if(loanApplicationDTO.getRequiredApprovalMatrix()!=null)
        	loanApplication.setRequiredApprovalMatrix(loanApplicationDTO.getRequiredApprovalMatrix());
        if(loanApplicationDTO.getFinalApprovalStatus()!=null)
        	loanApplication.setFinalApprovalStatus(ApprovalStatus.valueOf(loanApplicationDTO.getFinalApprovalStatus()));
        
        Optional.ofNullable(loanApplicationDTO.getFinalApprovalTimestamp())
        	.ifPresent(loanApplication::setFinalApprovalTimestamp);

        loanApplication.setIsActive(loanApplicationDTO.getIsActive());
        return loanApplication;
    }


    public List<LoanApplicationDTO> getAllLoanApplications() {
        return loanApplicationRepository.findByIsActiveTrue().stream()
            .map(this::convertToDTO)
            .toList();
    }
    
    public List<LoanApplicationDTO> getRecentLoanApplications() {
        return loanApplicationRepository.findByIsActiveTrue().stream()
            .map(this::convertToDTO)
            .toList().subList(0, 4);
    }
    
    public List<LoanApplicationDTO> getFinalApprovalPendingLoanApplications() {
        return loanApplicationRepository.findByFinalApprovalStatusFalseAndIsActiveTrue().stream()
            .map(this::convertToDTO)
            .toList();
    }
    
    public List<LoanApplicationDTO> getFinalApprovalCompletedLoanApplications() {
        return loanApplicationRepository.findByFinalApprovalStatusTrueAndIsActiveTrue().stream()
            .map(this::convertToDTO)
            .toList();
    }
    
    public List<LoanApplicationDTO> getAllActiveApplications() {
    	return loanApplicationRepository.findByIsActiveTrue().stream().map(this::convertToDTO).toList();
    }
    
    public List<LoanApplicationDTO> getAllInactiveApplications() {
    	return loanApplicationRepository.findByIsActiveFalse().stream().map(this::convertToDTO).toList();
    }
    
    public List<LoanApplicationDTO> getLoanApplicationsByIds(List<UUID> loanIdList) {
    	return loanApplicationRepository.findByLoanIdIn(loanIdList).stream().map(this::convertToDTO).toList();
    }
    
    public LoanApplicationDTO getLoanApplicationById(UUID loanId) {
        return loanApplicationRepository.findById(loanId)
            .map(this::convertToDTO)
            .orElse(null);
    }
    
    public LoanApplicationDTO updateFinalApprovalStatus(UUID loanId, boolean finalApproval) {
         LoanApplication loanApplication = loanApplicationRepository.findById(loanId).orElse(null);
         if(loanApplication!=null) {
        
        	 loanApplication.setFinalApprovalStatus(finalApproval ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED);
        	 loanApplication.setFinalApprovalTimestamp(new Timestamp(System.currentTimeMillis()));
        	 LoanApplication submittedLoanApplication = loanApplicationRepository.save(loanApplication);
        	 return convertToDTO(submittedLoanApplication);
         }
         return null;
    }
    
    public LoanApplicationDTO createLoanApplication(LoanApplicationDTO loanApplicationDTO) {
        LoanApplication loanApplication = convertToEntity(loanApplicationDTO);

        String applicationStatusString = ApplicationStatus.UNDER_REVIEW.toString();
        loanApplication.setApplicationStatus(ApplicationStatus.valueOf(applicationStatusString.toUpperCase()));

        loanApplication.setFinalApprovalStatus(ApprovalStatus.PENDING);
        loanApplication.setIsActive(true);
        
        if(loanApplication.getRiskLevel().equals(RiskLevel.LOW))
        	loanApplication.setRequiredApprovalMatrix(3);
        else if(loanApplication.getRiskLevel().equals(RiskLevel.MEDIUM))
        	loanApplication.setRequiredApprovalMatrix(4);
        else 
        	loanApplication.setRequiredApprovalMatrix(5);
        
        loanApplication.setFinalApprover(userRepository.findById(UserPrincipal.getCurrentUserId()).orElse(null));
        
        LoanApplication savedApplication = loanApplicationRepository.save(loanApplication);
        LoanApplicationDTO savedApplicationDTO = convertToDTO(savedApplication);
        
        savedApplicationDTO.setComplianceOfficerId(loanApplicationDTO.getComplianceOfficerId());
        savedApplicationDTO.setRiskAnalystId(loanApplicationDTO.getRiskAnalystId());
        savedApplicationDTO.setUnderwriterId(loanApplicationDTO.getUnderwriterId());
        savedApplicationDTO.setManagerId(loanApplicationDTO.getManagerId());
        savedApplicationDTO.setSeniorManagerId(loanApplicationDTO.getSeniorManagerId());
        
        createLowRiskApprovalEntries(savedApplicationDTO);
        
        if(loanApplication.getRiskLevel().equals(RiskLevel.MEDIUM)) {
        	createMediumRiskApprovalEntries(savedApplicationDTO);
        } else if(loanApplication.getRiskLevel().equals(RiskLevel.HIGH)){
        	createMediumRiskApprovalEntries(savedApplicationDTO);
        	createHighiskApprovalEntries(savedApplicationDTO);
        }
        return convertToDTO(loanApplication);
    }

    public LoanApplicationDTO updateLoanApplication(LoanApplicationDTO loanApplicationDTO) {
        Optional<LoanApplication> existingLoanApplication = loanApplicationRepository.findById(loanApplicationDTO.getLoanId());
        if (existingLoanApplication.isPresent()) {
            LoanApplication updatedLoanApplication = convertToEntity(loanApplicationDTO);
            updatedLoanApplication.setLoanId(loanApplicationDTO.getLoanId());
            updatedLoanApplication.setApplicationStatus(ApplicationStatus.UNDER_REVIEW);
            updatedLoanApplication.setFinalApprovalStatus(ApprovalStatus.PENDING);
            updatedLoanApplication.setIsActive(true);
            
            if(updatedLoanApplication.getRiskLevel().equals(RiskLevel.LOW))
            	updatedLoanApplication.setRequiredApprovalMatrix(3);
            else if(updatedLoanApplication.getRiskLevel().equals(RiskLevel.MEDIUM))
            	updatedLoanApplication.setRequiredApprovalMatrix(4);
            else 
            	updatedLoanApplication.setRequiredApprovalMatrix(5);
            
            //Delete existing approval entries first
            loanApprovalRepostiory.deleteByLoanId(loanApplicationDTO.getLoanId());
            
            createLowRiskApprovalEntries(loanApplicationDTO);
            
            if(updatedLoanApplication.getRiskLevel().equals(RiskLevel.MEDIUM)) {
            	createMediumRiskApprovalEntries(loanApplicationDTO);
            } else if(updatedLoanApplication.getRiskLevel().equals(RiskLevel.HIGH)){
            	createMediumRiskApprovalEntries(loanApplicationDTO);
            	createHighiskApprovalEntries(loanApplicationDTO);
            }
            
            LoanApplication savedLoanApplication = loanApplicationRepository.save(updatedLoanApplication);
            return convertToDTO(savedLoanApplication);
        }
        return null;
    }


    public void InValidateLoanApplication(UUID loanId) {
        Optional<LoanApplication>  loan= loanApplicationRepository.findById(loanId);
        if(loan.isPresent()) {
        	LoanApplication loanDisable=loan.get();
        	loanDisable.setIsActive(false);
        }
        
    }
    
    private boolean createLowRiskApprovalEntries(LoanApplicationDTO loanApplicationDTO) {
    	if(loanApplicationDTO.getUnderwriterId()!=null && loanApplicationDTO.getRiskAnalystId()!=null
    			&& loanApplicationDTO.getComplianceOfficerId()!=null) {
    		LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationDTO.getLoanId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid loan ID"));

            User underwriter = userRepository.findById(loanApplicationDTO.getUnderwriterId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid underwriter ID"));
            User riskAnalyst = userRepository.findById(loanApplicationDTO.getRiskAnalystId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid riskAnalyst ID"));
            User complianceOfficer = userRepository.findById(loanApplicationDTO.getComplianceOfficerId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid complianceOfficer ID"));

            LocalDateTime now = LocalDateTime.now();
            
    		LoanApproval underwriterApproval = new LoanApproval();
    		underwriterApproval.setApprovalLevel(1);
    		underwriterApproval.setApprovalStatus(ApprovalStatus.PENDING);
    		underwriterApproval.setLoan(loanApplication);
    		underwriterApproval.setApprover(underwriter);
    		
            LocalDateTime fiveDaysLater = now.plusDays(5);
            Timestamp underwriterSLA = Timestamp.valueOf(fiveDaysLater);
    		underwriterApproval.setSLA(underwriterSLA);
    		loanApprovalRepostiory.save(underwriterApproval);
    		createNewAssignmentNotification(loanApplicationDTO.getLoanId(), loanApplicationDTO.getUnderwriterId());
    		
    		LoanApproval riskAnalystApproval = new LoanApproval();
    		riskAnalystApproval.setApprovalLevel(2);
    		riskAnalystApproval.setApprovalStatus(ApprovalStatus.PENDING);
    		riskAnalystApproval.setLoan(loanApplication);
    		riskAnalystApproval.setApprover(riskAnalyst);
    		
            LocalDateTime tenDaysLater = now.plusDays(10);
            Timestamp riskAnalystSLA = Timestamp.valueOf(tenDaysLater);
            riskAnalystApproval.setSLA(riskAnalystSLA);
    		loanApprovalRepostiory.save(riskAnalystApproval);
    		createNewAssignmentNotification(loanApplicationDTO.getLoanId(), loanApplicationDTO.getRiskAnalystId());
    		
    		LoanApproval complianceApproval = new LoanApproval();
    		complianceApproval.setApprovalLevel(3);
    		complianceApproval.setApprovalStatus(ApprovalStatus.PENDING);
    		complianceApproval.setLoan(loanApplication);
    		complianceApproval.setApprover(complianceOfficer);
    		
            LocalDateTime fifteenDaysLater = now.plusDays(15);
            Timestamp compliancetSLA = Timestamp.valueOf(fifteenDaysLater);
            complianceApproval.setSLA(compliancetSLA);
    		loanApprovalRepostiory.save(complianceApproval);
    		createNewAssignmentNotification(loanApplicationDTO.getLoanId(), loanApplicationDTO.getComplianceOfficerId());
    		return true;
    		
    	} else
    		return false;
    }
    
    private boolean createMediumRiskApprovalEntries(LoanApplicationDTO loanApplicationDTO) {
    	if(loanApplicationDTO.getManagerId()!=null) {
    		LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationDTO.getLoanId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid loan ID"));

            User manager = userRepository.findById(loanApplicationDTO.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid manager ID"));
            
            LoanApproval managerApproval = new LoanApproval();
            managerApproval.setApprovalLevel(4);
            managerApproval.setApprovalStatus(ApprovalStatus.PENDING);
            managerApproval.setLoan(loanApplication);
            managerApproval.setApprover(manager);
    		
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime twentyDaysLater = now.plusDays(20);
            Timestamp managerSLA = Timestamp.valueOf(twentyDaysLater);
            managerApproval.setSLA(managerSLA);
    		LoanApproval submittedApproval = loanApprovalRepostiory.save(managerApproval);
    		createNewAssignmentNotification(loanApplicationDTO.getLoanId(), loanApplicationDTO.getManagerId());
    		return submittedApproval.getApprovalId()!=null;
    		
    	} else
    		return false;
    }
    
    private boolean createHighiskApprovalEntries(LoanApplicationDTO loanApplicationDTO) {
    	if(loanApplicationDTO.getSeniorManagerId()!=null) {
    		LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationDTO.getLoanId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid loan ID"));

            User seniorManager = userRepository.findById(loanApplicationDTO.getSeniorManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid senior manager ID"));
            
            LoanApproval seniorManagerApproval = new LoanApproval();
            seniorManagerApproval.setApprovalLevel(5);
            seniorManagerApproval.setApprovalStatus(ApprovalStatus.PENDING);
            seniorManagerApproval.setLoan(loanApplication);
            seniorManagerApproval.setApprover(seniorManager);
    		
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime twentyFiveDaysLater = now.plusDays(25);
            Timestamp seniorManagerSLA = Timestamp.valueOf(twentyFiveDaysLater);
            seniorManagerApproval.setSLA(seniorManagerSLA);
    		LoanApproval submittedApproval = loanApprovalRepostiory.save(seniorManagerApproval);
    		createNewAssignmentNotification(loanApplicationDTO.getLoanId(), loanApplicationDTO.getSeniorManagerId());
    		return submittedApproval.getApprovalId()!=null;
    		
    	} else
    		return false;
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
    
    public LoanOfficerDashboardMetrics getLoanOfficerMetrics() {
    	LoanOfficerDashboardMetrics metrics = new LoanOfficerDashboardMetrics();
    	metrics.setTotalApplications(loanApplicationRepository.count());
    	metrics.setApplicationsApproved(loanApplicationRepository.countByFinalApprovalStatusAndIsActiveTrue(ApprovalStatus.APPROVED));
    	metrics.setApplicationsPendingFinalApproval(loanApplicationRepository.countByApplicationStatusAndFinalApprovalStatusAndIsActiveTrue(ApplicationStatus.APPROVED, ApprovalStatus.PENDING));
    	metrics.setApplicationsUnderReview(loanApplicationRepository.countByApplicationStatusAndIsActiveTrue(ApplicationStatus.UNDER_REVIEW));
    	metrics.setApplicationsRejected(loanApplicationRepository.countByApplicationStatusAndIsActiveTrue(ApplicationStatus.REJECTED));
    	return metrics;
    }
    
}
