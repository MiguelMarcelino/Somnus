package com.somnus.server.backend.teammembers.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ContributionDto implements Serializable {
    private String title;
    private int teamMemberId;
    private String description;
    private LocalDateTime dateAdded;

    public ContributionDto() {}

    public ContributionDto(String title, int contributorId,
                           String description, LocalDateTime dateAdded){
        this.title = title;
        this.teamMemberId = contributorId;
        this.description = description;
        this.dateAdded = dateAdded;
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
