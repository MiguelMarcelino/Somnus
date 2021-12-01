package com.somnus.server.backend.feedbackService.feedback;

import com.somnus.server.backend.feedbackService.feedback.dto.FeedbackDto;
import com.somnus.server.backend.feedbackService.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/feedback-api")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping(value = "/feedback/create")
    public void createFeedback(@RequestBody User user, @RequestBody FeedbackDto feedbackDto) {
        feedbackService.deliverFeedback(user, user.getEmail(), feedbackDto);
    }
}
