package com.angaar.quiz_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angaar.quiz_service.models.entitlements.ResourceEntitlement;
import com.angaar.quiz_service.models.entitlements.ResourceType;
import com.angaar.quiz_service.models.entitlements.TargetType;

public interface ResourceEntitlementRepository extends JpaRepository<ResourceEntitlement, String>{
	Optional<ResourceEntitlement> findByResourceTypeAndResourceIdAndTargetTypeAndTargetId(ResourceType resourceType, String resourceId, TargetType targetType, String targetId);    
    List<ResourceEntitlement> findByTargetTypeAndTargetId(TargetType targetType, String targetId);
    List<ResourceEntitlement> findByResourceTypeAndTargetTypeAndTargetId(ResourceType resourceType, TargetType targetType, String targetId);
    List<ResourceEntitlement> findByResourceIdAndResourceType(String resourceId, ResourceType resourceType);
    
}
