package com.kramar.data.web;

import com.kramar.data.dto.WebExceptionInfo;
import com.kramar.data.exception.ForbiddenException;
import com.kramar.data.exception.GenericException;
import com.kramar.data.exception.NotAuthorizedException;
import com.kramar.data.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class HttpErrorAdvice {

    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    WebExceptionInfo handleForbiddenException(final ForbiddenException e) {
        return handleException(e);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    WebExceptionInfo handleNotAuthorizedException(final NotAuthorizedException e) {
        return handleException(e);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    WebExceptionInfo handleNotFoundException(final ResourceNotFoundException e) {
        return handleException(e);
    }

    private WebExceptionInfo handleException(GenericException e) {
        if (e instanceof ForbiddenException || e instanceof NotAuthorizedException || e instanceof ResourceNotFoundException) {
            log.error(e.getLocalStackTrace());
        } else {
            log.error(e.getFullStackTrace());
        }
        return new WebExceptionInfo(e);
    }
}
