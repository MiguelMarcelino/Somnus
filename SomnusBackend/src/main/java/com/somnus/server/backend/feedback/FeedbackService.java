package com.somnus.server.backend.feedback;

import com.somnus.server.backend.emailApi.EmailConfig;
import com.somnus.server.backend.emailApi.SomnusMailSender;
import com.somnus.server.backend.feedback.domain.Feedback;
import com.somnus.server.backend.feedback.dto.FeedbackDto;
import com.somnus.server.backend.feedback.repository.FeedbackRepository;
import com.somnus.server.backend.users.domain.User;
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
        Feedback feedback = new Feedback(user, feedbackDto.getTitle(), feedbackDto.getContent());
        feedbackRepository.save(feedback);

        // send email with feedback to admin
        mailSender.sendEmailToAdmin(EmailConfig.FEEDBACK_TOPIC.message + feedback.getTitle(),
                feedbackDto.getContent());

        // send email to user
        mailSender.sendEmail(userEmail, EmailConfig.FEEDBACK_RESPONSE_TOPIC.message,
                EmailConfig.FEEDBACK_RESPONSE_MESSAGE.message);
    }
}
