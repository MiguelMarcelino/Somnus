package com.somnus.server.backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {
    @Autowired
    private Environment environments;

    private static MongoClient mongoClient;

    public DatabaseConnection() {}

    public void startMongoDBConnection() {
        String mongoDBUri = environments.getProperty("spring.data.mongodb.uri");
        this.mongoClient = MongoClients.create(mongoDBUri);
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }


}
