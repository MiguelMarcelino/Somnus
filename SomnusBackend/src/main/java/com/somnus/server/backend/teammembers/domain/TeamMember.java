package com.somnus.server.backend.teammembers.domain;

import com.somnus.server.backend.config.DateHandler;

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
