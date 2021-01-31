package com.somnus.server.backend.teammembers.domain;

import com.somnus.server.backend.config.DateHandler;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

public class TeamMember {

    private ObjectId id;

    @BsonProperty(value = "team_member_name")
    private String teamMemberName;

    @BsonProperty(value = "photo_path")
    private String photoPath;

    @BsonProperty(value = "date_joined")
    private LocalDateTime dateJoined;

    public TeamMember() {}

    /**
     * Used when creating new TeamMembers
     * @param teamMemberName
     * @param photoPath
     */
    public TeamMember(String teamMemberName, String photoPath){
        this.teamMemberName = teamMemberName;
        this.photoPath = photoPath;
        this.dateJoined = DateHandler.now();
    }

    public String getTeamMemberName() {
        return teamMemberName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public LocalDateTime getDateJoined() {
        return dateJoined;
    }

}
