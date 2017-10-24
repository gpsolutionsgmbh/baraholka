package com.kramar.data.exception;

public class ConflictException extends GenericException {

    private static final long serialVersionUID = 8217861113397221204L;

    public ConflictException(final String message) {
        super(message);
    }

    public ConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ConflictException(final ErrorReason errorReason, final Object... reasonParams) {
        super(errorReason, reasonParams);
    }

    public ConflictException(final ErrorReason errorReason, final Throwable cause) {
        super(errorReason, cause);
    }

    public ConflictException(final ErrorReason errorReason, final Object reasonParam, final Throwable cause) {
        super(errorReason, reasonParam, cause);
    }

    public ConflictException(final ErrorReason errorReason, final Object reasonParam1, final Object reasonParam2, final Throwable cause) {
        super(errorReason, reasonParam1, reasonParam2, cause);
    }
}