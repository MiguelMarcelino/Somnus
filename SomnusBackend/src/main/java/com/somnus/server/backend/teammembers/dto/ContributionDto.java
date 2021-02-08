package com.somnus.server.backend.teammembers.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ContributionDto implements Serializable {
    private String title;
    private int teamMemberId;
    private String author;
    private LocalDateTime dateAdded;

    public ContributionDto() {}

    public ContributionDto(String title, String author, LocalDateTime dateAdded){
        this.title = title;
        // this.teamMemberId = contributorId;
        this.author = author;
        this.dateAdded = dateAdded;
    }

    public String getTitle() {
        return title;
    }

    public int getTeamMemberId() {
        return teamMemberId;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }
}
