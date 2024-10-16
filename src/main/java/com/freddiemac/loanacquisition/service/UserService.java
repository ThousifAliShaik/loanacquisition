package com.freddiemac.loanacquisition.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//
import com.freddiemac.loanacquisition.DTO.UserDTO;
import com.freddiemac.loanacquisition.entity.User;
import com.freddiemac.loanacquisition.entity.UserRole;
import com.freddiemac.loanacquisition.repository.UserRepository;

@Service
public class UserService {
	 private final UserRepository userRepository;

	    public UserService(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }

	private UserDTO convertToDTO(User user) {
        return new UserDTO(
            user.getUserId(),
            user.getUsername(),
            user.getPassword(), 
            user.getSecretKey(),
            user.getLastLogin(),
            user.getRole().name(), 
            user.getIsActive()
        );
    }

    // Convert UserDTO to User entity
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setSecretKey(userDTO.getSecretKey());
        user.setLastLogin(userDTO.getLastLogin());
        user.setIsActive(userDTO.getIsActive());
        return user;
    }
	
	public boolean createUser(UserDTO userDTO) {
		
		User user = convertToEntity(userDTO);
		if(user != null) {
        userRepository.save(user);
        return true;
		}
		return false;
	}
	 public List<UserDTO> getAllUsers() {
	        return userRepository.findAll().stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	    }
	 
	 public List<UserDTO> getAllActiveUsers() {
		    return userRepository.findByIsActive(true).stream()
		        .map(this::convertToDTO)
		        .collect(Collectors.toList());
		}
	 
	 public List<UserDTO> getRole(UserRole role) {
		    return userRepository.findByRole(role).stream()
		        .map(this::convertToDTO)
		        .collect(Collectors.toList());
		}


	 
	 
	 public UserDTO updateUser(UUID userId, UserDTO userDTO) {
	        Optional<User> existingUser = userRepository.findById(userId);
	        if (existingUser.isPresent()) {
	            User updatedUser = convertToEntity(userDTO);
	            updatedUser.setUserId(userId); 
	            User savedUser = userRepository.save(updatedUser);
	            return convertToDTO(savedUser);
	        }
	        return null;
	 }
	
	 public void disableUser(UUID userId) {
	       Optional<User> user= userRepository.findById(userId);
	       if(user.isPresent()) {
	    	   User userToDisable=user.get();
	    	   userToDisable.setIsActive(false);
	       }
    }
	
}


