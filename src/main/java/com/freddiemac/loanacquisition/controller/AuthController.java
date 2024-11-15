package com.freddiemac.loanacquisition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freddiemac.loanacquisition.entity.User;
import com.freddiemac.loanacquisition.entity.UserProfile;
import com.freddiemac.loanacquisition.repository.UserProfileRepository;
import com.freddiemac.loanacquisition.security.ApiResponse;
import com.freddiemac.loanacquisition.security.JwtAuthenticationResponse;
import com.freddiemac.loanacquisition.security.JwtTokenProvider;
import com.freddiemac.loanacquisition.security.LoginRequest;
import com.freddiemac.loanacquisition.security.PasswordEncryptionUtil;
import com.freddiemac.loanacquisition.security.SignUpRequest;
import com.freddiemac.loanacquisition.security.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws AuthenticationException {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the raw password matches the stored hashed password
        if (!PasswordEncryptionUtil.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()  // Use raw password for authentication
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user.getUserId(), user.getUserProfile().getRole().getRoleName().name()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (!userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Entered email is not registered!"));
        } else if (userRepository.existsByEmail(signUpRequest.getEmail()) &&
                userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Username is already taken!"));
        }
        UserProfile userProfile = userProfileRepository.findByEmail(signUpRequest.getEmail())
        		.orElseThrow(() -> new RuntimeException("UserProfile not found"));
        User user = userRepository.findByEmail(signUpRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        userProfile.setUsername(signUpRequest.getUsername());
        userProfileRepository.saveAndFlush(userProfile);
        
        user.setUsername(signUpRequest.getUsername());
        
        // Hash the password
        user.setPassword(PasswordEncryptionUtil.hashPassword(signUpRequest.getPassword()));
        
        user.setIsActive(true);
        User result = userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        // Clear the security context
        SecurityContextHolder.clearContext();

        // Optionally implement token invalidation logic here

        // Respond with a success message
        return ResponseEntity.ok(new ApiResponse(true, "User logged out successfully"));
    }
}