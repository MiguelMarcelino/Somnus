package com.somnus.server.backend.teammembers.database;

import com.somnus.server.backend.genericDatabaseConnection.ObjectDatabaseConnection;
import com.somnus.server.backend.teammembers.domain.TeamMember;
import org.springframework.stereotype.Component;

@Component
public class TeamMemberDatabaseConnection extends ObjectDatabaseConnection<TeamMember> {

    private static final String teamMemberCollectionName = "teamMembers";

    public TeamMemberDatabaseConnection() {
        super(teamMemberCollectionName);
    }


}
