package com.somnus.server.backend.exceptions;

import com.somnus.server.backend.config.DateHandler;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SomnusExceptionDto implements Serializable {

    private String message;
    private String debugMessage;
    private LocalDateTime errorTimestamp;

    public SomnusExceptionDto () {}

    public SomnusExceptionDto(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
        this.errorTimestamp = DateHandler.now();
    }
}
