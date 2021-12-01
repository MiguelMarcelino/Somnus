package com.somnus.server.postservice.articleComments.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.somnus.server.postservice.config.DateHandler;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "username")
    private String username;

    @Column(name = "user_display_name")
    private String userDisplayName;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "edited_at")
    private LocalDateTime editedAt;

    @Lob
    private byte[] content;

    @Column(name = "num_likes")
    private Integer numLikes;

    @ElementCollection
    private List<Integer> userLikes;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Comment.class,
            cascade = CascadeType.MERGE)
    @JsonBackReference
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Comment> responseComments;

    public Comment() {
    }

    /**
     * Constructor used to create a new comment
     *
     * @param articleId       - Article to which the comment belongs to
     * @param username        - Username of the user that published the comment
     * @param userDisplayName - Display name of the user that published the comment
     * @param content         - Content of the commend
     */
    public Comment(Integer articleId, String username, String userDisplayName,
                   String content) {
        this.articleId = articleId;
        this.username = username;
        this.userDisplayName = userDisplayName;
        this.publishedAt = DateHandler.now();
        this.editedAt = null;
        this.numLikes = 0;
        this.parentComment = null;
        this.responseComments = new ArrayList<>();
        this.userLikes = new ArrayList<>();
        this.content = parseContent(content);
    }

    public Integer getId() {
        return id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserDisplayName() {
        return userDisplayName;
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

    public Comment getParentComment() {
        return parentComment;
    }

    public List<Integer> getUserLikes() {
        return userLikes;
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

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public void addResponseComment(Comment comment) {
        if (responseComments == null) {
            responseComments = new ArrayList<>();
        }
        responseComments.add(comment);
    }

    private byte[] parseContent(String content) {
        byte[] commentContent = content.getBytes(StandardCharsets.UTF_8);
        return commentContent;
    }

    public void addUserLikes(Integer userID) {
        if (userLikes == null) {
            userLikes = new ArrayList<>();
        }
        userLikes.add(userID);
    }

    public void removeUserLike(Integer userID) {
        if (userLikes != null) {
            userLikes.remove(userID);
        }
    }
}
