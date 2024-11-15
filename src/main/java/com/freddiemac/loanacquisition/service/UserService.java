package com.freddiemac.loanacquisition.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freddiemac.loanacquisition.dto.AdminDashboardMetrics;
import com.freddiemac.loanacquisition.dto.UserDTO;
import com.freddiemac.loanacquisition.entity.User;
import com.freddiemac.loanacquisition.security.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	private UserDTO convertToDTO(User user) {
		return new UserDTO(user.getUserId(), user.getUserProfile().getFullName(), user.getEmail(), user.getUsername(),
				user.getLastLogin(), user.getRoleId(), user.getIsActive());
	}

	// Convert UserDTO to User entity
	private User convertToEntity(UserDTO userDTO) throws Exception {
		User user = new User();
		user.setIsActive(false);
		user.setRoleId(userDTO.getRoleId());
		user.setEmail(userDTO.getEmail());

		return user;
	}

	public User createUser(UserDTO userDTO) throws Exception {

		User user = convertToEntity(userDTO);

		return userRepository.save(user);
	}
	
	public User findByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		return user.isPresent() ? user.get() : null;
	}
	
	public UserDTO findByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user.isPresent() ? convertToDTO(user.get()) : null;
	}
	
	public String getNameByUserId(UUID userId) {
		Optional<User> user = userRepository.findById(userId);
		return user.isPresent() ? user.get() .getUserProfile().getFullName(): null;
	}

	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(this::convertToDTO).toList();
	}

	public List<UserDTO> getAllActiveUsers() {
		return userRepository.findByIsActive(true).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public List<UserDTO> getByRole(UUID roleId) {
		return userRepository.findByRoleId(roleId).stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	public boolean isUsernameTaken(String username) {
		return userRepository.findByUsername(username).isPresent();
	}
	
	public boolean userExists(UUID userId) {
		return userRepository.findById(userId).isPresent();
	}

	public boolean userExists(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
	
	public User updateUser(UUID userId, UserDTO userDTO) throws Exception {
		Optional<User> existingUser = userRepository.findById(userId);
		if (existingUser.isPresent()) {
			User updatedUser = convertToEntity(userDTO);
			updatedUser.setUserId(userId);
			User savedUser = userRepository.save(updatedUser);
			return savedUser;
		}
		return null;
	}

	public boolean disableUser(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			User userToDisable = user.get();
			userToDisable.setIsActive(false);
			userRepository.save(userToDisable);
			return true;
		}
		return false;
	}
	
	public boolean enableUser(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			User userToEnable = user.get();
			userToEnable.setIsActive(true);
			userRepository.save(userToEnable);
			return true;
		}
		return false;
	}

	public AdminDashboardMetrics getAdminMetrics() {
		AdminDashboardMetrics metrics = new AdminDashboardMetrics();
		metrics.setTotalUsers(userRepository.count());
		metrics.setActiveUsers(userRepository.countByIsActiveTrue());
		metrics.setDisabledUsers(userRepository.countByUsernameIsNotNullAndIsActiveFalse());
		metrics.setPendingRegistrations(userRepository.countByUsernameIsNullAndIsActiveFalse());
		return metrics;
	}
}
