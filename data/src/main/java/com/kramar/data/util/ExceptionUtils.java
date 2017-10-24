package com.kramar.data.util;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolationException;

public class ExceptionUtils extends org.apache.commons.lang3.exception.ExceptionUtils {

    private static final String CAUSE_CAPTION = "\nCaused by: ";
    private static final String PACKAGE_FILTER = "com.kramar.baraholka";
    private static final int CAUSE_DEPTH = 5;

    /**
     * @param throwable the throwable to get the root cause for, may be null
     * @return the root cause message of the <code>Throwable</code>,
     * <p> root cause class name, if rootCause is present, but message is <code>null</code>,
     * <p> empty string, if rootCause none found or null throwable input
     */
    public static String getRootCauseMessage(final Throwable throwable) {
        final Throwable root = ExceptionUtils.getRootCause(throwable);
        if (root == null) {
            return StringUtils.EMPTY;
        } else if (root instanceof ConstraintViolationException) {
            // Special case for ConstraintViolationException
            return parseConstraintViolationException((ConstraintViolationException) root);

        } else if (root.getMessage() != null) {
            return StringUtils.trimToEmpty(root.getMessage());
        } else {
            return ClassUtils.getShortClassName(root, null);
        }

    }

    public static String getMessage(final Throwable th) {
        if (th == null) {
            return StringUtils.EMPTY;
        }
        return StringUtils.trimToEmpty(th.getMessage());
    }

    /**
     * @param throwable the throwable to get the most comprehensive error description
     * @return the both: original and  root cause messages of the <code>Throwable</code>,
     * <p> only original cause message, if root cause message is empty,
     * <p> only root cause message, if original message is empty
     */
    public static String getFullMessage(final Throwable throwable) {
        final String message;
        if (throwable instanceof ConstraintViolationException) {
            message = parseConstraintViolationException((ConstraintViolationException) throwable);
        } else if (throwable instanceof NullPointerException) {
            message = "NullPointerException";
        } else {
            message = getMessage(throwable);
        }

        final String rootMessage = getRootCauseMessage(throwable);

        if (StringUtils.isEmpty(rootMessage)) {
            return message;
        } else if (StringUtils.isEmpty(message)) {
            return rootMessage;
        } else if (message.equals(rootMessage)) {
            return message;
        }

        return message + ". " + rootMessage;

    }

    public static String getLocalStackTrace(final Throwable throwable) {
        final StringBuilder stackTraceAccumulator = new StringBuilder(throwable.toString());
        try {
            for (StackTraceElement traceElement : throwable.getStackTrace()) {
                if (traceElement.getClassName().startsWith(PACKAGE_FILTER))
                    stackTraceAccumulator.append("\n\tat ").append(traceElement);
            }
            addCauseStackTrace(throwable, CAUSE_DEPTH, stackTraceAccumulator);
        } catch (RuntimeException e) {
//            log.error("Unable to build LocalStackTrace: {}", getRootCauseMessage(e));
        }
        return stackTraceAccumulator.toString();
    }

    private static void addCauseStackTrace(final Throwable original, int depth, final StringBuilder stackTraceAccumulator) {
        if (original == null || depth < 1) {
            return;
        }

        final Throwable ourCause = original.getCause();
        if (ourCause != null && ourCause != original) {
            stackTraceAccumulator.append(CAUSE_CAPTION).append(ourCause);
            StackTraceElement[] stackTrace = ourCause.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                stackTraceAccumulator.append("\n\tat ").append(stackTrace[0]);
            }
            addCauseStackTrace(ourCause, depth--, stackTraceAccumulator);
        }
    }

    private static String parseConstraintViolationException(final ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map((violation) -> violation.getMessage() + ", invalid value: " + violation.getInvalidValue() + ". ")
                .reduce(StringUtils.EMPTY, String::concat);
    }
}

