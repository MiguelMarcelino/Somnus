package com.somnus.server.backend.articleComments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommentDto implements Serializable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("articleId")
    private Integer articleId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("userDisplayName")
    private String userDisplayName;

    @JsonProperty("publishedAt")
    private String publishedAt;

    @JsonProperty("editedAt")
    private String editedAt;

    @JsonProperty("content")
    private String content;

    @JsonProperty("numLikes")
    private Integer numLikes;

    @JsonProperty("responseComments")
    private List<CommentDto> responseComments;

    @JsonProperty("isUserLikedComment")
    private boolean isUserLikedComment;

    @JsonProperty("parentId")
    private Integer parentId;

    public CommentDto() {
    }

    public CommentDto(Integer id, Integer articleId, String username, String userDisplayName, LocalDateTime publishedAt,
                      LocalDateTime editedAt, String content, Integer numLikes, Integer parentId) {
        this.id = id;
        this.articleId = articleId;
        this.username = username;
        this.userDisplayName = userDisplayName;
        this.publishedAt = publishedAt.format(DateTimeFormatter.ISO_DATE);
        if (editedAt != null) {
            this.editedAt = editedAt.format(DateTimeFormatter.ISO_DATE);
        }
        this.content = content;
        this.numLikes = numLikes;
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
    }

    public void setResponseComments(List<CommentDto> responseComments) {
        this.responseComments = responseComments;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setUserLikedComment(boolean userLikedComment) {
        isUserLikedComment = userLikedComment;
    }
}
