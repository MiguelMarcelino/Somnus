package com.somnus.server.backend.teammembers.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContributionDto implements Serializable {
    private String title;
    private int teamMemberId;
    private String author;
    private LocalDateTime dateAdded;
    private String dateAddedString;

    public ContributionDto() {}

    public ContributionDto(String title, String author, LocalDateTime dateAdded){
        this.title = title;
        // this.teamMemberId = contributorId;
        this.author = author;
        this.dateAdded = dateAdded;
        this.dateAddedString = dateAdded.format(DateTimeFormatter.ISO_DATE);
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

    public String getDateAddedString() {
        return dateAddedString;
    }
}
