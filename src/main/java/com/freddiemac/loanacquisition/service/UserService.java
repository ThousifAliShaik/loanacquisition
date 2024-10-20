package com.freddiemac.loanacquisition.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freddiemac.loanacquisition.dto.UserDTO;
import com.freddiemac.loanacquisition.entity.User;
import com.freddiemac.loanacquisition.repository.UserRepository;
import com.freddiemac.loanacquisition.security.PasswordEncryptionUtil;

@Service
public class UserService {

	private static final String NEW_USER_PASSWORD = "password";

	@Autowired
	private UserRepository userRepository;

	private UserDTO convertToDTO(User user) {
		return new UserDTO(user.getUserId(), user.getUsername(), user.getPassword(), user.getSecretKey(),
				user.getLastLogin(), user.getRoleId(), user.getIsActive());
	}

	// Convert UserDTO to User entity
	private User convertToEntity(UserDTO userDTO) throws Exception {
		User user = new User();
		user.setIsActive(true);
		user.setRoleId(userDTO.getRoleId());
		user.setSecretKey(PasswordEncryptionUtil.generateSecretKey());
		user.setPassword(PasswordEncryptionUtil.encryptPassword(NEW_USER_PASSWORD, user.getSecretKey()));
		user.setUsername(userDTO.getUsername());

		return user;
	}

	public User createUser(UserDTO userDTO) throws Exception {

		User user = convertToEntity(userDTO);
		if (user != null) {

			return userRepository.save(user);
		}
		return null;
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}

	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public List<UserDTO> getAllActiveUsers() {
		return userRepository.findByIsActive(true).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public List<UserDTO> getRole(UUID roleId) {
		return userRepository.findByRoleId(roleId).stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	public boolean isUsernameTaken(String username) {
		return userRepository.findByUsername(username).isPresent();
	}
	
	public boolean userExists(UUID userId) {
		return userRepository.findById(userId).isPresent();
	}

	public UserDTO updateUser(UUID userId, UserDTO userDTO) throws Exception {
		Optional<User> existingUser = userRepository.findById(userId);
		if (existingUser.isPresent()) {
			User updatedUser = convertToEntity(userDTO);
			updatedUser.setUserId(userId);
			User savedUser = userRepository.save(updatedUser);
			return convertToDTO(savedUser);
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

}
