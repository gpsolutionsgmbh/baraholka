package com.kramar.data.exception;

public enum ErrorReason {

    INTERNAL_SERVER_ERROR("Internal Server Error"),
    USER_NOT_FOUND("User with provided %s can't be found"),
    RESOURCE_NOT_FOUND("Resource (%s) was not found"),
    BLOCKED_ACCOUNT("Your account was blocked. Please contact us <email> to get more details"),
    UNABLE_TO_CONVERT_IMAGE("Unable to convert image"),
    INVALID_UPLOAD_FILE_TYPE("Incorrect upload file type"),
    INVALID_PASSWORD("Invalid current password, please try again"),
    INVALID_PERMISSION("Invalid permission"),
    INVALID_EMAIL("Email you have chosen already exists, please try another one");

    private String description;

    ErrorReason(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
