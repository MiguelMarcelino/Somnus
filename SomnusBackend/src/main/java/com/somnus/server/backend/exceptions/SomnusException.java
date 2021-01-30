package com.somnus.server.backend.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SomnusException extends RuntimeException {
    private static final Logger somnusLogger = LoggerFactory.getLogger(SomnusException.class);

    public SomnusException(ErrorMessage errorMessage) {
        super(errorMessage.message);
        somnusLogger.error(errorMessage.message);
    }

    public SomnusException(ErrorMessage errorMessage, Exception e) {
        super(errorMessage.message, e);
        somnusLogger.error(errorMessage.message, e);
    }

}
