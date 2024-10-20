package com.freddiemac.loanacquisition.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freddiemac.loanacquisition.dto.RoleDTO;
import com.freddiemac.loanacquisition.dto.UserProfileDTO;
import com.freddiemac.loanacquisition.entity.Role;
import com.freddiemac.loanacquisition.entity.User;
import com.freddiemac.loanacquisition.entity.UserProfile;
import com.freddiemac.loanacquisition.entity.UserRole;
import com.freddiemac.loanacquisition.repository.UserProfileRepository;

@Service
public class UserProfileService {
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private UserService userService;
	
	private UserProfileDTO convertToDTO(UserProfile userProfile) {
		UserProfileDTO userProfileDTO = new UserProfileDTO();
		userProfileDTO.setUserId(userProfile.getUserId());
		userProfileDTO.setUsername(userProfile.getUsername());
		userProfileDTO.setEmail(userProfile.getEmail());
		userProfileDTO.setFullName(userProfile.getFullName());
		userProfileDTO.setRole(new RoleDTO(userProfile.getRole().getRoleId(), userProfile.getRole().getRoleName().name()));
		userProfileDTO.setPhoneNumber(userProfile.getPhoneNumber());
		userProfileDTO.setCreatedAt(userProfile.getCreatedAt());
		userProfileDTO.setUpdatedAt(userProfile.getUpdatedAt());
		return userProfileDTO;
	}
	
	private UserProfile convertFromDTO(UserProfileDTO userProfileDTO) {
		UserProfile userProfile = new UserProfile();
		userProfile.setEmail(userProfileDTO.getEmail());
		userProfile.setFullName(userProfileDTO.getFullName());
		userProfile.setPhoneNumber(userProfileDTO.getPhoneNumber());
		userProfile.setUsername(userProfileDTO.getUsername());
		userProfile.setRoleId(userProfileDTO.getRole().getRoleId());
		return userProfile;
	}
	
	public List<UserProfileDTO> getAllUserProfiles() {
		return userProfileRepository.findAll().stream()
		        .map(this::convertToDTO)
		        .collect(Collectors.toList());
	}
	
	public List<UserProfileDTO> getActiveUserProfiles() {
		return userProfileRepository.findByUserIsActiveTrue().stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}
	
	public List<UserProfileDTO> getInactiveUserProfiles() {
		return userProfileRepository.findByUserIsActiveFalse().stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}
	
	public Boolean createNewUserProfile(UserProfileDTO userProfile) throws Exception {
		if(userService.isUsernameTaken(userProfile.getUsername())) {
			return false;
		} else {
			UserProfile newUserProfile = new UserProfile();
			User user = userService.createUser(userProfile.getUser());
			if(user==null) {
				return false;
			} else {
				newUserProfile.setEmail(userProfile.getEmail());
				newUserProfile.setFullName(userProfile.getFullName());
				newUserProfile.setPhoneNumber(userProfile.getPhoneNumber());
				newUserProfile.setRoleId(userProfile.getRole().getRoleId());
				newUserProfile.setUser(user);
				newUserProfile.setRole(new Role(
								userProfile.getRole().getRoleId(), 
								UserRole.valueOf(userProfile.getRole().getRoleName()))
							);
				newUserProfile.setUsername(userProfile.getUsername());
				userProfileRepository.save(newUserProfile);
			}
			
		}
		return true;
	}
	
}
