package com.freddiemac.loanacquisition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freddiemac.loanacquisition.dto.RoleDTO;
import com.freddiemac.loanacquisition.dto.UserProfileDTO;
import com.freddiemac.loanacquisition.service.RoleService;
import com.freddiemac.loanacquisition.service.UserProfileService;
import com.freddiemac.loanacquisition.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/all_users")
	public ResponseEntity<List<UserProfileDTO>> listAllUsers() {
		List<UserProfileDTO> userProfiles = userProfileService.getAllUserProfiles();
		if(!userProfiles.isEmpty())
			return ResponseEntity.ok(userProfiles);
		else
			return new ResponseEntity<>(userProfiles, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/active_users")
	public ResponseEntity<List<UserProfileDTO>> listActiveUsers() {
		List<UserProfileDTO> userProfiles = userProfileService.getActiveUserProfiles();
		
		if(!userProfiles.isEmpty())
			return ResponseEntity.ok(userProfiles);
		else
			return new ResponseEntity<>(userProfiles, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/inactive_users")
	public ResponseEntity<List<UserProfileDTO>> listInactiveUsers() {
		List<UserProfileDTO> userProfiles = userProfileService.getInactiveUserProfiles();
		
		if(!userProfiles.isEmpty())
			return ResponseEntity.ok(userProfiles);
		else
			return new ResponseEntity<>(userProfiles, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/all_roles")
	public ResponseEntity<List<RoleDTO>> listAllRoles() {
		List<RoleDTO> roles = roleService.getAllRoles();
		if(!roles.isEmpty())
			return ResponseEntity.ok(roles);
		else
			return new ResponseEntity<>(roles, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/new_user")
	public ResponseEntity<String> createNewUser(@RequestBody UserProfileDTO userProfile) {
		
		return new ResponseEntity<>("User added !!", HttpStatus.CREATED);
	}
	
	@PostMapping("/disable_user")
	public ResponseEntity<String> disableUser(@RequestParam String username) {
		System.out.println("Reached here!");
		if(userService.isUsernameTaken(username)) {
			System.out.println("Reached here!");
			userService.disableUser(username);
			return new ResponseEntity<>("User disabled !!", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("User not found !", HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/enable_user")
	public ResponseEntity<String> enableUser(@RequestParam String username) {
		if(userService.isUsernameTaken(username)) {
			System.out.println("Reached here!");
			userService.enableUser(username);
			return new ResponseEntity<>("User disabled !!", HttpStatus.ACCEPTED);
		}
			return new ResponseEntity<>("User not found !", HttpStatus.NOT_FOUND);
	}
}
