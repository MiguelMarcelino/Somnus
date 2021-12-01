package com.somnus.server.postservice.post.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class PostDTO implements Serializable {
    private String id;
    private String postName;
    private String normalizedName;
    private String authorUserName;
    private String userId;
    private String description;
    private String datePublished;
    private String lastUpdate;
    private String content;
    private boolean isDeleted;

    public PostDTO() {}

    public PostDTO(String id, String postName, String normalizedName, String authorUserName, String userId, String description,
                    LocalDateTime datePublished, LocalDateTime lastUpdate, boolean isDeleted, String content) {
        this.id = id;
        this.postName = postName;
        this.normalizedName = normalizedName;
        this.authorUserName = authorUserName;
        this.userId = userId;
        this.description = description;
        this.datePublished = datePublished.format(DateTimeFormatter.ISO_DATE);
        if(lastUpdate != null) {
            this.lastUpdate = lastUpdate.format(DateTimeFormatter.ISO_DATE);
        }
        this.content = content;
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public String getPostName() {
        return postName;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
