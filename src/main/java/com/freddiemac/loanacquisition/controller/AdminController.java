package com.freddiemac.loanacquisition.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freddiemac.loanacquisition.dto.AdminDashboardMetrics;
import com.freddiemac.loanacquisition.dto.RoleDTO;
import com.freddiemac.loanacquisition.dto.UserProfileDTO;
import com.freddiemac.loanacquisition.security.ApiResponse;
import com.freddiemac.loanacquisition.service.RoleService;
import com.freddiemac.loanacquisition.service.UserProfileService;
import com.freddiemac.loanacquisition.service.UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RolesAllowed("ADMIN")
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class AdminController {
	
	private static final String USER_NOT_FOUND = "User not found in registry !";
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/all_users")
	@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
	public ResponseEntity<List<UserProfileDTO>> listAllUsers() {
		List<UserProfileDTO> userProfiles = userProfileService.getAllUserProfiles();
		if(!userProfiles.isEmpty())
			return ResponseEntity.ok(userProfiles);
		else
			return new ResponseEntity<>(userProfiles, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<UserProfileDTO> getUser(@PathVariable UUID id) {
		UserProfileDTO userProfile = userProfileService.getUserProfileById(id);
		if(userProfile!=null)
			return ResponseEntity.ok(userProfile);
		else
			return new ResponseEntity<>(userProfile, HttpStatus.NOT_FOUND);
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
	public ResponseEntity<ApiResponse> createNewUser(@Valid @RequestBody UserProfileDTO userProfile) {
	    if (userService.userExists(userProfile.getEmail())) {
	        return ResponseEntity
	                .badRequest()
	                .body(new ApiResponse(false, "Email is already registered!"));
	    }

	    try {
	        userProfileService.createNewUserProfile(userProfile);
	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(new ApiResponse(true, "New User added !!"));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiResponse(false, "Adding New User Failed !!"));
	    }
	}
	
	@GetMapping("/dashboard_metrics")
	public ResponseEntity<AdminDashboardMetrics> getAdminDashboardMetrics() {
		return ResponseEntity.ok(userService.getAdminMetrics());
	}

	
	@PutMapping("/update_user")
	public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserProfileDTO userProfile) {
		boolean userUpdated = false;
		if(userProfileService.getUserProfileById(userProfile.getUserId())!=null) {
			try {
				userUpdated = userProfileService.updateUserProfile(userProfile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, USER_NOT_FOUND));
		}
		if(userUpdated)
			return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(new ApiResponse(true, "User updated !!"));
		else
			return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(new ApiResponse(false, "Updating user failed !!"));
	}
	
	@PutMapping("/disable_user")
	public ResponseEntity<ApiResponse> disableUser(@RequestParam String username) {
		if(userService.isUsernameTaken(username)) {
			userService.disableUser(username);
			return ResponseEntity
	                .status(HttpStatus.ACCEPTED)
	                .body(new ApiResponse(true, "User disabled !!"));
		}
		return ResponseEntity
                .badRequest()
                .body(new ApiResponse(false, USER_NOT_FOUND));
	}
	
	@PutMapping("/enable_user")
	public ResponseEntity<ApiResponse> enableUser(@RequestParam String username) {
		if(userService.isUsernameTaken(username)) {
			userService.enableUser(username);
			return ResponseEntity
	                .status(HttpStatus.ACCEPTED)
	                .body(new ApiResponse(true, "User enabled !!"));
		}
		return ResponseEntity
                .badRequest()
                .body(new ApiResponse(false, USER_NOT_FOUND));
	}
}
