package com.kramar.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kramar.data.exception.GenericException;
import lombok.Getter;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@JsonInclude(NON_EMPTY)
public class WebExceptionInfo implements Serializable {

    private static final long serialVersionUID = 4437900283180609523L;

    private Long timestamp = System.currentTimeMillis();
    private String msg;
    private String error;
    private Object detailedError;

    public WebExceptionInfo(GenericException e) {
        error = e.getErrorReason().name();
        msg = e.getFullMessage();
        detailedError = e.errorDetails;
    }
}
