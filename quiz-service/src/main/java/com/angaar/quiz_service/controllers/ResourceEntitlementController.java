package com.angaar.quiz_service.controllers;

import com.angaar.quiz_service.models.entitlements.Role;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resource-entitlement")
public class ResourceEntitlementController {
    
    @GetMapping("/currentRoles")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<?> getRoles() {
    	System.out.println("rteceived");
    	return ResponseEntity.ok(Role.values());
    }


}

