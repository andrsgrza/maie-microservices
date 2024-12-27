package com.angaar.login_service.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.angaar.login_service.jwt.JwtUtil;
import com.angaar.login_service.models.User;
import com.angaar.login_service.models.Vault;
import com.angaar.login_service.models.dto.LoginRequestDTO;
import com.angaar.login_service.models.dto.LoginResponseDTO;
import com.angaar.login_service.models.dto.UserDTO;
import com.angaar.login_service.repositories.UserRepository;
import com.angaar.login_service.repositories.VaultRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:8082", allowCredentials = "true")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private VaultRepository vaultRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @Transactional  // Ensure both Vault and User are saved or rolled back together
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if username is already taken
        
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }

        // Create a Vault entry for the user's password
        Vault vault = new Vault();
        vault.setSecret(passwordEncoder.encode(user.getPassword()));  // Store hashed password
        vault = vaultRepository.save(vault);  // Save the Vault entry

        // Link the Vault entry with the User
        user.setPasswordVault(vault);        

        // Save the User (this will also persist the Vault due to the transaction)
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    // Login endpoint (Sign-in)
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response ) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        if(!authentication.isAuthenticated()) {
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication method failed");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername().trim());
        String jwt;
        if(user.isPresent()) {
        	jwt = jwtUtil.generateToken(authentication, user.get().getId());
        	ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", jwt)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(jwtUtil.getJwtExpirationMs() / 1000)
                    .sameSite("Strict")  // Set SameSite attribute
                    .build();

            // Add the cookie to the response headers
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok("User authenticated successfully");
        } else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
   
        
        //return new ResponseEntity<LoginResponseDTO>(new LoginResponseDTO(jwt), HttpStatus.OK);    
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Create a cookie with the same name as the JWT token and set it to expire
        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")  // Set SameSite attribute
                .build();

        // Add the expired cookie to the response
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("User logged out successfully");
    }
    
}