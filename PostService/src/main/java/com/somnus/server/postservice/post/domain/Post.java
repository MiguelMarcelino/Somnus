package com.somnus.server.postservice.post.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.somnus.server.postservice.config.DateHandler;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // The database author id
    @Column(name = "user_id")
    private Integer authorId;

    // The id generated by Firebase
    @Column(name = "firebase_generated_user_id")
    private String firebaseGeneratedUserID;

    @Column(name = "post_name")
    private String postName;

    @Column(name = "author_user_name")
    private String authorUserName;

    @Column(name = "description")
    private String description;

    @Column(name = "last_update")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime lastUpdate = null;

    @Column(name = "date_published")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime datePublished;

    private boolean isDeleted;

    @Lob
    private byte[] content;

    public Post() {
    }

    /**
     * Create a new Article for storing in Database
     *
     * @param userID
     * @param postName
     * @param authorUserName
     * @param description
     * @param content
     */
    public Post(Integer userID, String postName, String authorUserName, String firebaseGeneratedUserID, String description, String content) {
        this.authorId = userID;
        this.postName = postName;
        this.authorUserName = authorUserName;
        this.firebaseGeneratedUserID = firebaseGeneratedUserID;
        this.description = description;
        this.datePublished = DateHandler.now();
        this.lastUpdate = DateHandler.now();
        this.isDeleted = false;
        writeData(content);
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public String getFirebaseGeneratedUserID() {
        return firebaseGeneratedUserID;
    }

    public String getPostName() {
        return postName;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDatePublished() {
        return datePublished;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getContent() {
        return new String(content, StandardCharsets.UTF_8);
    }

    public void setPostName(String newsPostName) {
        this.postName = newsPostName;
    }

    public void updateLastUpdate() {
        this.lastUpdate = DateHandler.now();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        writeData(content);
    }

    public boolean getIsDeleted(){
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    private void writeData(String content) {
        byte[] contentData = content.getBytes(StandardCharsets.UTF_8);
        this.content = contentData;
    }
}
