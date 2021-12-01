package com.somnus.server.backend.feedbackService.config;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateHandler {

    private DateHandler() {}

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}