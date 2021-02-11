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

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    @Column(name = "author")
    private String author;

    @Column(name = "date_added")
    private LocalDateTime dateAdded;

    public Contribution() {}

    /**
     * Constructor used when adding a new Contribution
     * @param title
     * @param teamMember
     * @param author
     * @param dateAdded
     */
    public Contribution(String title,  String author, LocalDateTime dateAdded, TeamMember teamMember) {
        this.title = title;
        this.author = author;
        this.teamMember = teamMember;
        this.dateAdded = dateAdded;
    }

    public String getTitle() {
        return title;
    }

    public TeamMember getTeamMember() {
        return teamMember;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }
}
