package com.somnus.server.backend.articles.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.somnus.server.backend.users.dto.UserDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommentDto implements Serializable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("articleId")
    private Integer articleId;

    @JsonProperty("user")
    private UserDto user;

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

    @JsonProperty("parentId")
    private Integer parentId;

    public CommentDto() {
    }

    public CommentDto(Integer id, Integer articleId, UserDto user, LocalDateTime publishedAt,
                      LocalDateTime editedAt, String content, Integer numLikes) {
        this.id = id;
        this.articleId = articleId;
        this.user = user;
        this.publishedAt = publishedAt.format(DateTimeFormatter.ISO_DATE);
        if (editedAt != null) {
            this.editedAt = editedAt.format(DateTimeFormatter.ISO_DATE);
        }
        this.content = content;
        this.numLikes = numLikes;
        this.responseComments = responseComments;
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
}
