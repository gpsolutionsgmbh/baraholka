package com.kramar.data.exception;

public class ResourceNotFoundException extends GenericException {

    private static final long serialVersionUID = -2627375146388552744L;

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(final ErrorReason errorReason, Object... reasonParams) {
        super(errorReason, reasonParams);
    }

    public ResourceNotFoundException(final ErrorReason errorReason, final Throwable cause) {
        super(errorReason, cause);
    }

    public ResourceNotFoundException(final ErrorReason errorReason, final Object reasonParam, final Throwable cause) {
        super(errorReason, reasonParam, cause);
    }

    public ResourceNotFoundException(final ErrorReason errorReason, final Object reasonParam1, final Object reasonParam2, final Throwable cause) {
        super(errorReason, reasonParam1, reasonParam2, cause);
    }
}
