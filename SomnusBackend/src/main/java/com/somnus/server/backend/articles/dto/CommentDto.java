package com.somnus.server.backend.articles.dto;

import com.somnus.server.backend.users.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommentDto {
    private Integer id;
    private Integer articleId;
    private UserDto user;
    private String publishedAt;
    private String editedAt;
    private String content;
    private Integer numLikes;
    private List<CommentDto> responseComments;
    private Integer parentId;

    public CommentDto() {}

    public CommentDto(Integer id, Integer articleId, UserDto user, LocalDateTime publishedAt,
                       LocalDateTime editedAt, String content, Integer numLikes) {
        this.id = id;
        this.articleId = articleId;
        this.user = user;
        this.publishedAt = publishedAt.format(DateTimeFormatter.ISO_DATE);
        this.editedAt = editedAt.format(DateTimeFormatter.ISO_DATE);
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
