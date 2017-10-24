package com.kramar.data.exception;

import com.kramar.data.util.ExceptionUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public abstract class GenericException extends RuntimeException {

    private static final long serialVersionUID = 6850550271805120499L;

    @Setter
    protected ErrorReason errorReason;

    @Getter
    @Setter
    public Object errorDetails;

    public ErrorReason getErrorReason() {
        return (errorReason == null ? ErrorReason.INTERNAL_SERVER_ERROR : errorReason);
    }

    public GenericException(final String message) {
        super(message);
    }

    public GenericException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GenericException(final ErrorReason errorReason, final Object... reasonParams) {
        super((reasonParams == null || (reasonParams.length) == 0) ?
                errorReason.getDescription() : String.format(errorReason.getDescription(), reasonParams));
        this.errorReason = errorReason;
    }

    public GenericException(final ErrorReason errorReason, final Throwable cause) {
        super(errorReason.getDescription(), cause);
        this.errorReason = errorReason;
    }

    public GenericException(final ErrorReason errorReason, final Object reasonParam, final Throwable cause) {
        super(String.format(errorReason.getDescription(), reasonParam), cause);
        this.errorReason = errorReason;
    }

    public GenericException(final ErrorReason errorReason, final Object reasonParam1, final Object reasonParam2, final Throwable cause) {
        super(String.format(errorReason.getDescription(), reasonParam1, reasonParam2), cause);
        this.errorReason = errorReason;
    }

    public String getFullMessage() {
        return ExceptionUtils.getFullMessage(this);
    }

    public String getFullStackTrace() {
        return ExceptionUtils.getStackTrace(this);
    }

    public String getLocalStackTrace() {
        return ExceptionUtils.getLocalStackTrace(this);
    }

}

