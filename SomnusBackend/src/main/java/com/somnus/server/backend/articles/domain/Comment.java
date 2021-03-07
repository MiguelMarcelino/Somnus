package com.somnus.server.backend.articles.domain;

import com.somnus.server.backend.config.DateHandler;
import com.somnus.server.backend.users.domain.User;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "article_id")
    private Integer articleId;

    @OneToOne(optional = false)
    private User user;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "edited_at")
    private LocalDateTime editedAt;

    @Lob
    private byte[] content;

    @Column(name = "num_likes")
    private Integer numLikes;

    @OneToMany
    private List<Comment> responseComments;

    public Comment() {}

    /**
     * Constructor used to create a new comment
     * @param articleId - Article to which the comment belongs to
     * @param user - User that published the comment
     * @param content - Content of the commend
     */
    public Comment(Integer articleId, User user,
                   String content) {
        this.articleId = articleId;
        this.user = user;
        this.publishedAt = DateHandler.now();
        this.editedAt = null;
        this.numLikes = 0;
        this.content = parseContent(content);
        this.responseComments = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public String getContent() {
        return new String(content, StandardCharsets.UTF_8);
    }

    public Integer getNumLikes() {
        return numLikes;
    }

    public List<Comment> getResponseComments() {
        return responseComments;
    }

    public void setNumLikes(Integer numLikes) {
        this.numLikes = numLikes;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public void setContent(String content) {
        this.content = parseContent(content);
    }

    public void addResponseComment(Comment comment) {
        if(responseComments == null) {
            responseComments = new ArrayList<>();
        }
        responseComments.add(comment);
    }

    private byte[] parseContent(String content) {
        byte[] commentContent = content.getBytes(StandardCharsets.UTF_8);
        return commentContent;
    }
}
