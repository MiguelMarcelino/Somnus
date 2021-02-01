package com.somnus.server.backend.feedback;

import com.somnus.server.backend.feedback.dto.FeedbackDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/contacts-api")
public class FeedbackController {

    @Autowired
    private FeedbackService contactsService;

    @PostMapping(value = "deliverFeedback")
    public void deliverFeedback(@RequestBody FeedbackDto feedbackDto) {
        contactsService.deliverFeedback(feedbackDto);
    }
}
