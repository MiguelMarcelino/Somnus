package com.somnus.server.backend.teammembers.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TeamMemberDto implements Serializable {

    private String teamMemberName;
    private String photoPath;
    private LocalDateTime dateJoined;

    public TeamMemberDto() {}

    /**
     * Used when creating new TeamMember
     * @param teamMemberName
     * @param photoPath
     */
    public TeamMemberDto(String teamMemberName, String photoPath) {
        this.teamMemberName = teamMemberName;
        this.photoPath = photoPath;
    }

    /**
     * Used to send TeamMember information to frontend
     * @param teamMemberName
     * @param photoPath
     * @param dateJoined
     */
    public TeamMemberDto(String teamMemberName, String photoPath,
                         LocalDateTime dateJoined) {
        this.teamMemberName = teamMemberName;
        this.photoPath = photoPath;
        this.dateJoined = dateJoined;
    }

    public String getTeamMemberName() {
        return teamMemberName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

}
