package com.somnus.server.backend.feedback.domain;

import com.somnus.server.backend.config.DateHandler;
import com.somnus.server.backend.users.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Lob
    private String content;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "read_by_admin", nullable = false)
    private boolean readByAdmin;

    @Lob
    private String response;


    public Feedback() {
    }

    /**
     * Used when adding new feedback
     */
    public Feedback(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.submissionDate = DateHandler.now();
        this.status = Status.UNRESOLVED;
        this.readByAdmin = false;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public Status getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
