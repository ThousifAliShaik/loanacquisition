package com.freddiemac.loanacquisition.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freddiemac.loanacquisition.dto.RoleDTO;
import com.freddiemac.loanacquisition.dto.UserDTO;
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
		if(userProfile.getUser()!=null)
			userProfileDTO.setIsActive(userProfile.getUser().getIsActive() != null && userProfile.getUser().getIsActive());
		else
			userProfileDTO.setIsActive(false);
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
	
	public UserProfileDTO getUserProfileById(UUID userId) {
		return userProfileRepository.findById(userId).map(this::convertToDTO).orElse(null);
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
		System.out.println(userProfile.toString());
		if(userService.isUsernameTaken(userProfile.getUsername())) {
			return false;
		} else {
			UserProfile newUserProfile = new UserProfile();
			
			
			UserDTO newUser = new UserDTO();
			newUser.setIsActive(false);
			newUser.setEmail(userProfile.getEmail());
			newUser.setRoleId(userProfile.getRole().getRoleId());
			User user = userService.createUser(newUser);
			
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
				userProfileRepository.save(newUserProfile);
			}
			
		}
		return true;
	}
	
	public Boolean updateUserProfile(UserProfileDTO userProfile) throws Exception {
		if(userService.isUsernameTaken(userProfile.getUsername())) {
			return false;
		} else {
			UserProfile oldUserProfile = userProfileRepository.findById(userProfile.getUserId()).orElse(null);
			
			if(oldUserProfile!=null) {
				User user = null;
				if(userProfile.getUser()==null) {
					UserDTO oldUser = userService.findByEmail(userProfile.getEmail());
					oldUser.setRoleId(userProfile.getRole().getRoleId());
					user = userService.updateUser(oldUser.getUserId(), oldUser);
				} else {
					userProfile.getUser().setRoleId(userProfile.getRole().getRoleId());
					
					user = userService.updateUser(oldUserProfile.getUser().getUserId(),userProfile.getUser());
				}
				
				if(user!=null) {
					oldUserProfile.setFullName(userProfile.getFullName());
					oldUserProfile.setPhoneNumber(userProfile.getPhoneNumber());
					oldUserProfile.setRoleId(userProfile.getRole().getRoleId());
					oldUserProfile.setUser(user);
					oldUserProfile.setRole(new Role(
									userProfile.getRole().getRoleId(), 
									UserRole.valueOf(userProfile.getRole().getRoleName()))
								);
					oldUserProfile.setUsername(user.getUsername());
					userProfileRepository.save(oldUserProfile);
				}
			}
			
		}
		return true;
	}
	
}
