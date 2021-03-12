package com.somnus.server.backend.articleComments.domain;

import com.somnus.server.backend.config.DateHandler;
import com.somnus.server.backend.users.domain.User;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_liked_comments",
            joinColumns = {
                    @JoinColumn(name = "comment_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id")
            }
    )
    private Map<Integer, User> userLikes;

    //    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Comment.class)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment",
            cascade = {CascadeType.PERSIST, CascadeType.ALL},
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
        this.userLikes = new HashMap<>();
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

    public Map<Integer, User> getUserLikes() {
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

    public void addUserLikes(User user) {
        if (userLikes == null) {
            userLikes = new HashMap<>();
        }
        userLikes.put(user.getId(), user);
    }

    public void removeUserLike(User user) {
        if (userLikes != null) {
            userLikes.remove(user.getId());
        }
    }
}
