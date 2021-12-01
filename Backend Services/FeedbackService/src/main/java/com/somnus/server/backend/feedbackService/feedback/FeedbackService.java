package com.somnus.server.backend.feedbackService.feedback;

import com.somnus.server.backend.feedbackService.emailApi.EmailConfig;
import com.somnus.server.backend.feedbackService.emailApi.SomnusMailSender;
import com.somnus.server.backend.feedbackService.feedback.domain.Feedback;
import com.somnus.server.backend.feedbackService.feedback.dto.FeedbackDto;
import com.somnus.server.backend.feedbackService.feedback.repository.FeedbackRepository;
import com.somnus.server.backend.feedbackService.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private SomnusMailSender mailSender;

    public void deliverFeedback(User user, String userEmail, FeedbackDto feedbackDto) {
        // save Feedback Information
        Feedback feedback = new Feedback(user.getId(), feedbackDto.getTitle(), feedbackDto.getContent());
        feedbackRepository.save(feedback);

        // send email with feedback to admin
        mailSender.sendEmailToAdmin(user.getEmail(), EmailConfig.FEEDBACK_TOPIC.message + feedback.getTitle(),
                feedbackDto.getContent());

        // send email to user
        mailSender.sendEmail(user.getEmail(), EmailConfig.FEEDBACK_RESPONSE_TOPIC.message,
                EmailConfig.FEEDBACK_RESPONSE_MESSAGE.message);
    }
}
