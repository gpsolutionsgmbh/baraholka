package com.kramar.data.exception;

public class NotAuthorizedException extends GenericException {

    private static final long serialVersionUID = -2627375146388552744L;

    public NotAuthorizedException(final String message) {
        super(message);
    }

    public NotAuthorizedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizedException(final ErrorReason errorReason, final Object... reasonParams) {
        super(errorReason, reasonParams);
    }

    public NotAuthorizedException(final ErrorReason errorReason, final Throwable cause) {
        super(errorReason, cause);
    }

    public NotAuthorizedException(final ErrorReason errorReason, final Object reasonParam, final Throwable cause) {
        super(errorReason, reasonParam, cause);
    }

    public NotAuthorizedException(final ErrorReason errorReason, final Object reasonParam1, final Object reasonParam2, final Throwable cause) {
        super(errorReason, reasonParam1, reasonParam2, cause);
    }
}
