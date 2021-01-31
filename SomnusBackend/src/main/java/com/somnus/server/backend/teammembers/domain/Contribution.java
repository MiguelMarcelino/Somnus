package com.somnus.server.backend.teammembers.domain;

import com.somnus.server.backend.config.DateHandler;
import com.somnus.server.backend.users.domain.User;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class Contribution {

    private ObjectId id;

    @BsonProperty(value = "title")
    private String title;

    @BsonProperty(value = "team_member_id")
    private int teamMemberId;

    @BsonProperty(value = "description")
    private String description;

    @BsonProperty(value = "dateAdded")
    private LocalDateTime dateAdded;

    public Contribution() {}

    /**
     * Constructor used when adding a new Contribution
     * @param title
     * @param teamMemberId
     * @param description
     */
    public Contribution(String title, Integer teamMemberId, String description) {
        this.title = title;
        this.teamMemberId = teamMemberId;
        this.description = description;
        this.dateAdded = DateHandler.now();
    }

    public String getTitle() {
        return title;
    }

    public int getTeamMemberId() {
        return teamMemberId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }
}
