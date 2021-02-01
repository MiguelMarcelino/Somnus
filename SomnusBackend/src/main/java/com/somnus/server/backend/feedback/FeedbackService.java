package com.somnus.server.backend.feedback;

import com.somnus.server.backend.feedback.domain.Feedback;
import com.somnus.server.backend.feedback.dto.FeedbackDto;
import com.somnus.server.backend.feedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public void deliverFeedback(FeedbackDto feedbackDto) {
        // save Feedback Information
        Feedback feedback = new Feedback(feedbackDto.getTitle(), feedbackDto.getContent());
        feedbackRepository.save(feedback);

        // send email to admin

    }
}
