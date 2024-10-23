package com.angaar.quiz_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angaar.quiz_service.models.entitlements.ResourceEntitlement;
import com.angaar.quiz_service.models.entitlements.ResourceType;
import com.angaar.quiz_service.models.entitlements.Role;
import com.angaar.quiz_service.models.entitlements.TargetType;
import com.angaar.quiz_service.repositories.ResourceEntitlementRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceEntitlementService {
	
	private static final String GLOBAL_TARGET_ID = "GLOBAL_0000000"; // Use 0 as a special value for GLOBAL target types

    @Autowired
    private ResourceEntitlementRepository resourceEntitlementRepository;
    
    public List<ResourceEntitlement> getResourcesForTarget(String userId) {
    	List<ResourceEntitlement> resourceList = resourceEntitlementRepository.findByTargetTypeAndTargetId(null, userId);
    	if(!resourceList.isEmpty()) {
    		return resourceList;
    	}
    	return null;
    	
    }
    public Map<String, Role> getHighestRoleByResource(List<ResourceEntitlement> entitlements) {
        // Group entitlements by resourceId and get the highest role per resource
        return entitlements.stream()
            .collect(Collectors.toMap(
                ResourceEntitlement::getResourceId,   // Key: resourceId
                ResourceEntitlement::getRole,         // Value: role
                Role::getHigherRole                  // Merge function: get the higher role when duplicate resourceId occurs
            ));
    }

    public boolean hasPermission(String userId, ResourceType resourceType, String resourceId, Role requiredRole) {
        // Check if the user has a specific entitlement for this resource
        Optional<ResourceEntitlement> userEntitlement = resourceEntitlementRepository
            .findByResourceTypeAndResourceIdAndTargetTypeAndTargetId(resourceType, resourceId, TargetType.USER, userId);

        if (userEntitlement.isPresent() && userHasRequiredRole(userEntitlement.get().getRole(), requiredRole)) {
            return true;
        }

        // Check if there is a global entitlement for this resource
        Optional<ResourceEntitlement> globalEntitlement = resourceEntitlementRepository
            .findByResourceTypeAndResourceIdAndTargetTypeAndTargetId(resourceType, resourceId, TargetType.GLOBAL, null);

        return globalEntitlement.isPresent() && userHasRequiredRole(globalEntitlement.get().getRole(), requiredRole);
    }

    private boolean userHasRequiredRole(Role userRole, Role requiredRole) {
        // Owner has all permissions
        if (userRole == Role.OWNER) {
            return true;
        }
        // Check role hierarchy
        if (userRole == Role.READ_WRITE && (requiredRole == Role.READ_WRITE || requiredRole == Role.READ_ONLY)) {
            return true;
        }
        return userRole == Role.READ_ONLY && requiredRole == Role.READ_ONLY;
    }

    public ResourceEntitlement assignRole(String userId, ResourceType resourceType, String resourceId, TargetType targetType, Role role, boolean overwrite) {
        String targetId = targetType == TargetType.USER ? userId : GLOBAL_TARGET_ID;

        // Check if an entitlement already exists
        Optional<ResourceEntitlement> existingEntitlement = resourceEntitlementRepository
            .findByResourceTypeAndResourceIdAndTargetTypeAndTargetId(resourceType, resourceId, targetType, targetId);

        if (existingEntitlement.isPresent()) {
        	System.out.println("An entitlement on the selected target, for the selected resource already exists");
            if (overwrite) {
                // If overwrite is true, remove the existing entitlement
            	resourceEntitlementRepository.delete(existingEntitlement.get());
            	System.out.println("Overwrite is true. Existin entitlement " + existingEntitlement.get() + " has been removed.");
            } else {
                System.out.println("Overwrite is false. Not assigning new entitlement");
            }
        }

        // Create a new entitlement
        ResourceEntitlement entitlement = new ResourceEntitlement();
        entitlement.setResourceType(resourceType);
        entitlement.setResourceId(resourceId);
        entitlement.setTargetType(targetType);
        entitlement.setTargetId(targetId);
        entitlement.setRole(role);
        return resourceEntitlementRepository.save(entitlement);
    }
    public ResourceEntitlement findUserEntitlementsOnQuiz(String userId, String quizId) {    	
    	Optional<ResourceEntitlement> userEntitlement = resourceEntitlementRepository
    			.findByResourceTypeAndResourceIdAndTargetTypeAndTargetId(ResourceType.QUIZ, quizId, TargetType.USER, userId);
    	if(userEntitlement.isEmpty()) {
    		return null;
    	} else {
    		return userEntitlement.get();
    	}
    }
    public Map<String, Role> findQuizzesEntitledByUser(String userId) {    	
    	List<ResourceEntitlement> userEntitlements = resourceEntitlementRepository
    			.findByResourceTypeAndTargetTypeAndTargetId(ResourceType.QUIZ, userId, TargetType.USER);
    	if(userEntitlements.isEmpty()) {
    		return null;
    	} else {
    		Map<String, Role> entitlementMap = getHighestRoleByResource(userEntitlements);
    		return entitlementMap;
    	}
    }
    
}
