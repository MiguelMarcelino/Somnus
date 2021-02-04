package com.somnus.server.backend.feedback;

import com.somnus.server.backend.feedback.dto.FeedbackDto;
import com.somnus.server.backend.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/feedback-api")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping(value = "/deliverFeedback")
    public void deliverFeedback(Principal principal, @RequestBody FeedbackDto feedbackDto) {
        User user = (User) ((Authentication) principal).getPrincipal();

        feedbackService.deliverFeedback(user.getEmail(), feedbackDto);
    }
}
