package com.somnus.server.bakend.authservice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class SomnusExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger somnusLogger = LoggerFactory.getLogger(SomnusExceptionHandler.class);

    @ExceptionHandler(SomnusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SomnusExceptionDto somnusException(SomnusException e) {
        return new SomnusExceptionDto(ErrorMessage.BAD_REQUEST.message, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    private SomnusExceptionDto accessDeniedException(AccessDeniedException e) {
        somnusLogger.error(e.getMessage());
        return new SomnusExceptionDto(ErrorMessage.ACCESS_DENIED.message, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SomnusExceptionDto serverException(Exception e) {
        somnusLogger.error(e.getMessage(), e);
        return new SomnusExceptionDto(ErrorMessage.INTERNAL_SERVER_ERROR.message, e.getMessage());
    }
}
