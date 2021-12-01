package com.somnus.server.backend.teamMemberService.teammembers.domain;

import com.somnus.server.backend.teamMemberService.config.DateHandler;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "team_members")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "team_member_name")
    private String teamMemberName;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "date_joined")
    private LocalDateTime dateJoined;

    @Column(name = "num_contributions")
    private Integer numContributions;

    @Column(name = "github_username")
    private String githubUsername;

    public TeamMember() {}

    /**
     * Used when creating new TeamMembers
     * @param teamMemberName
     * @param photoPath
     * @param githubUsername
     */
    public TeamMember(String teamMemberName, String photoPath, String githubUsername){
        this.teamMemberName = teamMemberName;
        this.photoPath = photoPath;
        this.dateJoined = DateHandler.now();
        this.githubUsername = githubUsername;
        this.numContributions = 0;
    }
    
    /**
     * Used when registering a commit that does not belong to any existing TeamMember
     * @param githubUsername
     */
    public TeamMember(String githubUsername){
        this.dateJoined = DateHandler.now();
        this.githubUsername = githubUsername;
        this.numContributions = 0;
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

    public Integer getNumContributions() {
        return numContributions;
    }

    public String getGithubUsername(){
        return githubUsername;
    }

    public void addContribution() {
        numContributions++;
    }

    public void setNumContributions(Integer numContributions) {
        this.numContributions = numContributions;
    }

    public void setDateJoined(LocalDateTime dateJoined) {
        this.dateJoined = dateJoined;
    }
}
