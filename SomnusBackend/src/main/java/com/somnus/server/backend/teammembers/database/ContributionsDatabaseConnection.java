package com.somnus.server.backend.teammembers.database;

import com.somnus.server.backend.genericDatabaseConnection.ObjectDatabaseConnection;
import com.somnus.server.backend.teammembers.domain.Contribution;

public class ContributionsDatabaseConnection extends ObjectDatabaseConnection<Contribution> {

    private static final String contributionsDatabaseName = "contributions";

    public ContributionsDatabaseConnection() {
        super(contributionsDatabaseName);
    }
}
