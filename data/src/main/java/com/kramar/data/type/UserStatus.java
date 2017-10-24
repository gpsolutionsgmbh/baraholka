package com.kramar.data.type;

public enum UserStatus {

    ACTIVE("Active"),
    REMOVED("Removed"),
    BLOCKED("Blocked");

    private String status;

    UserStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
