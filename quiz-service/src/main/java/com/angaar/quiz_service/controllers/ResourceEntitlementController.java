package com.angaar.quiz_service.controllers;

import com.angaar.quiz_service.models.dto.QuizEntitlementRequestDTO;
import com.angaar.quiz_service.models.entitlements.ResourceEntitlement;
import com.angaar.quiz_service.models.entitlements.Role;
import com.angaar.quiz_service.service.ResourceEntitlementService;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resource-entitlement")
public class ResourceEntitlementController {
	@Autowired
	ResourceEntitlementService resourceEntitlementService;
    
    @GetMapping("/currentRoles")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<Role[]> getRoles() {
    	System.out.println("rteceived");
    	return ResponseEntity.ok(Role.values());
    }
    
    @GetMapping("/entitlements/{resourceId}")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<Map<Role, List<Map<String, String>>>> getEntitlements(@PathVariable String resourceId) {
    	System.out.println("rteceived");
    	Map<Role, List<Map<String, String>>> resourceMap = resourceEntitlementService.findEntitlementsForQuiz(resourceId);
    	return ResponseEntity.ok(resourceMap);
    }
    
    @PostMapping("/entitlements/quiz/{resourceId}")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<List<ResourceEntitlement>> addQuizEntitlement(
            @PathVariable String resourceId,
            @RequestBody QuizEntitlementRequestDTO requestBody) {
    	requestBody.getQuizUserList().forEach(user -> {
    	    System.out.println("User Id: " + user.getUserId());
    	    System.out.println("User Name: " + user.getUsername());
    	});   	

    	System.out.println("Adding quiz entitlement with resId " + resourceId + " and role " + requestBody.getRole() );
    	System.out.println("Processing users");
    	requestBody.getQuizUserList().forEach((user) -> user.print());
    	List<ResourceEntitlement> entitlements = requestBody.getQuizUserList().stream()
    			.map(quizEntitlementUser -> resourceEntitlementService.assignUserRoleToQuiz(quizEntitlementUser.getUserId(), resourceId, requestBody.getRole()))
    			.toList();

        return ResponseEntity.ok(entitlements);
    }
    
    // This handles a single QuiEntitlementRequest
    @DeleteMapping("/entitlements/quiz/{resourceId}")
    @CrossOrigin(origins = "http://localhost:8082")
    public ResponseEntity<Void> removeQuizEntitlement(
        @PathVariable String resourceId,
        @RequestBody QuizEntitlementRequestDTO requestBody
    ) {
    	requestBody.getQuizUserList().forEach(user -> {
    	    System.out.println("User Id: " + user.getUserId());
    	    System.out.println("User Name: " + user.getUsername());
    	});
    	System.out.println("Removing quiz entitlement from resId " + resourceId + " and role " + requestBody.getRole() );
    	
    	requestBody.getQuizUserList().forEach(quizEntitlementUser -> 
	        resourceEntitlementService.removeUserRoleFromQuiz(
	            quizEntitlementUser.getUserId(), resourceId, requestBody.getRole()
	        )
	    );
        return ResponseEntity.noContent().build();
    }
    
    /*
     * TODO: A single RequestObject that includes the operation, role, and users 
     * It should handle both Create and Delete operations
     */
//    @PostMapping("/entitlements/quiz/{resourceId}")
//    @CrossOrigin(origins = "http://localhost:8082")
//    public ResponseEntity<List<ResourceEntitlement>> handleQuizEntitlement(
//            @PathVariable String resourceId,
//            @RequestBody QuizEntitlementRequestDTO requestBody) {
//    	requestBody.getQuizUserList().forEach(user -> {
//    	    System.out.println("User Id: " + user.getUserId());
//    	    System.out.println("User Name: " + user.getUsername());
//    	});
//    	System.out.println("Request body: " + requestBody);
//    	System.out.println("Quiz User List: " + requestBody.getQuizUserList());
//
//    	System.out.println("Adding quiz entitlement with resId " + resourceId + " and role " + requestBody.getRole() );
//    	System.out.println("Processing users");
//    	requestBody.getQuizUserList().forEach((user) -> user.print());
//    	List<ResourceEntitlement> entitlements = requestBody.getQuizUserList().stream()
//    			.map(quizEntitlementUser -> resourceEntitlementService.assignUserRoleToQuiz(quizEntitlementUser.getUserId(), resourceId, requestBody.getRole()))
//    			.toList();
//
//        return ResponseEntity.ok(entitlements);
//    }


}

