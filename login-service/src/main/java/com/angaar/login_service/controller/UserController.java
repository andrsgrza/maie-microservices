package com.angaar.login_service.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angaar.login_service.jwt.JwtUtil;
import com.angaar.login_service.models.User;
import com.angaar.login_service.models.dto.BasicUserDTO;
import com.angaar.login_service.models.dto.UserDTO;
import com.angaar.login_service.repositories.UserRepository;


import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
//@CrossOrigin(origins = "http://localhost:8082", allowCredentials = "true")
public class UserController {
	@Autowired
    private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping
	public ResponseEntity<?> getUser(@RequestBody String userId) {
	    Optional<User> user = userRepository.findById(userId);	    	   
	    if (user.isPresent()) {
	        return ResponseEntity.ok(user.get());
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("User not found");
	    }
	}
	
	@GetMapping("/whoAmI")
    public ResponseEntity<?> getUserData(HttpServletRequest request) {
        // Extract token from Authorization header
        String token = jwtUtil.parseJwt(request);
        System.out.println("Received token" + token);
        
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        // Get the username or userId from the token
        String username = jwtUtil.getUsernameFromToken(token);

        // Fetch user from database
        Optional<User> user = userRepository.findByUsername(username);
        
        if (user.isPresent()) {
        	UserDTO userDto = new UserDTO();
        	userDto.setUserId(user.get().getId());
        	userDto.setEmail(user.get().getEmail());
        	userDto.setUsername(user.get().getUsername());
        	userDto.setProfilePicture(user.get().getProfilePicture());      
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
	
	@PostMapping("/batch")
	public ResponseEntity<Map<String, String>> getUsernamesByIds(@RequestBody List<String> userIds) {
	    Map<String, String> userIdToUsername = userRepository.findAllById(userIds).stream()
	        .collect(Collectors.toMap(User::getId, User::getUsername));
	    return ResponseEntity.ok(userIdToUsername);
	}
	
	@GetMapping("/search")
    public ResponseEntity<List<BasicUserDTO>> searchUsernames(@RequestParam String query) {
        List<BasicUserDTO> usernames = userRepository.findTop10ByUsernameContaining(query);
        return ResponseEntity.ok(usernames);
    }

}
