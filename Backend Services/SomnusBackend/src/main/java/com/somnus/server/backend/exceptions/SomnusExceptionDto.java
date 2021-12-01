package com.somnus.server.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.somnus.server.backend.config.DateHandler;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SomnusExceptionDto implements Serializable {

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "debug_message")
    private String debugMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime errorTimestamp;

    public SomnusExceptionDto () {}

    public SomnusExceptionDto(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
        this.errorTimestamp = DateHandler.now();
    }
}
