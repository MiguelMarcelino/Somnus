package com.somnus.server.backend.teammembers.domain;

import com.somnus.server.backend.config.DateHandler;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contributions")
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    @Column(name = "description")
    private String description;

    @Column(name = "date_added")
    private LocalDateTime dateAdded;

    public Contribution() {}

    /**
     * Constructor used when adding a new Contribution
     * @param title
     * @param teamMember
     * @param description
     */
    public Contribution(String title, TeamMember teamMember, String description) {
        this.title = title;
        this.teamMember = teamMember;
        this.description = description;
        this.dateAdded = DateHandler.now();
    }

    public String getTitle() {
        return title;
    }

    public TeamMember getTeamMember() {
        return teamMember;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }
}
