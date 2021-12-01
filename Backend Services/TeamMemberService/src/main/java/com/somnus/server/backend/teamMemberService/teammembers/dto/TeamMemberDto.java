package com.somnus.server.backend.teamMemberService.teammembers.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TeamMemberDto implements Serializable {

    private String teamMemberName;
    private String photoPath;
    private String dateJoined;
    private Integer numContributions;
    private String githubUsername;

    public TeamMemberDto() {}

    /**
     * Used when creating new TeamMember
     * @param teamMemberName
     * @param photoPath
     * @param githubUsername
     */
    public TeamMemberDto(String teamMemberName, String photoPath, String githubUsername) {
        this.teamMemberName = teamMemberName;
        this.photoPath = photoPath;
        this.githubUsername = githubUsername;
    }

    /**
     * Used to send TeamMember information to frontend
     * @param teamMemberName
     * @param photoPath
     * @param dateJoined
     * @param githubUsername
     */
    public TeamMemberDto(String teamMemberName, String photoPath,
                         LocalDateTime dateJoined, Integer numContributions, String githubUsername) {
        this.teamMemberName = teamMemberName;
        this.photoPath = photoPath;
        this.dateJoined = dateJoined.format(DateTimeFormatter.ISO_DATE);
        this.numContributions = numContributions;
        this.githubUsername = githubUsername;
    }

    public String getTeamMemberName() {
        return teamMemberName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public Integer getNumContributions() {
        return numContributions;
    }

    public String getGithubUsername(){
        return githubUsername;
    }
}
