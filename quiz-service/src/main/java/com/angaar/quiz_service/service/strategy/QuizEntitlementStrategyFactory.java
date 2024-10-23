package com.angaar.quiz_service.service.strategy;

import com.angaar.quiz_service.models.entitlements.ResourceEntitlement;
import com.angaar.quiz_service.models.entitlements.Role;

public class QuizEntitlementStrategyFactory {

    public static QuizEntitlementStrategy getStrategy(String userId, ResourceEntitlement entitlement) {

        // Check if the user is an owner
        if (entitlement.getRole() != null && entitlement.getRole() == Role.OWNER) {
            return new OwnerQuizStrategy();
        }

        // Check if the user has read-only access
        if (entitlement.getRole() != null && entitlement.getRole() == Role.READ_ONLY) {
            return new ReadOnlyQuizStrategy();
        }

        // Check if the user has read-write access
        if (entitlement.getRole() != null && entitlement.getRole() == Role.READ_WRITE) {
            return new ReadWriteQuizStrategy();
        }

        // If no entitlement is found, you can throw an exception or return a default strategy
        throw new IllegalArgumentException("No entitlement found for user: " + userId);
    }
}
