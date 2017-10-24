package com.kramar.data.exception;

public class ForbiddenException extends GenericException {

    private static final long serialVersionUID = -2627375146388552744L;

    public ForbiddenException(final String message) {
        super(message);
    }

    public ForbiddenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(final ErrorReason errorReason, final Object... reasonParams) {
        super(errorReason, reasonParams);
    }

    public ForbiddenException(final ErrorReason errorReason, final Throwable cause) {
        super(errorReason, cause);
    }

    public ForbiddenException(final ErrorReason errorReason, final Object reasonParam, final Throwable cause) {
        super(errorReason, reasonParam, cause);
    }

    public ForbiddenException(final ErrorReason errorReason, final Object reasonParam1, final Object reasonParam2, final Throwable cause) {
        super(errorReason, reasonParam1, reasonParam2, cause);
    }
}