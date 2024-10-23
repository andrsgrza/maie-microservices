package com.angaar.quiz_service.models.entitlements;

public enum Role {
	READ_ONLY(1), READ_WRITE(2), OWNER(3);

    private final int priority;

    Role(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public static Role getHigherRole(Role r1, Role r2) {
        return r1.getPriority() > r2.getPriority() ? r1 : r2;
    }
}
