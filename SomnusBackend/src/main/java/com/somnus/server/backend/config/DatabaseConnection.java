package com.somnus.server.backend.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {
    @Autowired
    private Environment environments;

    private static MongoDatabase database;

    public DatabaseConnection() {}

    public void startMongoDBConnection() {
        // mongoDBUri and database name from environments
        String mongoDBUri = environments.getProperty("spring.data.mongodb.uri");
        String databaseUri = environments.getProperty("spring.data.mongodb.database");

        // connection for mongodb including uri
        ConnectionString connectionString = new ConnectionString(mongoDBUri);

        // codec to handle translation from BSON to POJOs
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());

        // default codec registry (handles primitive types in java)
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);

        // all settings wrapped together
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            database = mongoClient.getDatabase(databaseUri);
        } catch (Exception e) {
            throw new SomnusException(ErrorMessage.DATABASE_CONNECTION_ERROR, e);
        }
    }

    public static MongoDatabase getMongoDatabase() {
        return database;
    }

}
