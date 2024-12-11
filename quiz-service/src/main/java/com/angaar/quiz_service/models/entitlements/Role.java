package com.angaar.quiz_service.models.entitlements;

public enum Role {
	OWNER(3), READ_WRITE(2), READ_ONLY(1);

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
