package com.somnus.server.backend.emailApi;

public enum EmailConfig {
    // Email Topics
    FEEDBACK_TOPIC("Feedback: "),
    FEEDBACK_RESPONSE_TOPIC("Feedback received by somnus admin"),

    // Email Responses
    FEEDBACK_RESPONSE_MESSAGE("Your Feedback has been received by the Somnus Administrator. " +
            "It will now be processed. You will receive an answer shortly! \n " +
            "Thank you for giving your Feedback to the somnus website.\n" +
            "Best Regards, \n Somnus Administration.");

    public final String message;

    EmailConfig(String message){
        this.message = message;
    }
}
