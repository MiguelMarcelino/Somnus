package com.somnus.server.backend.config;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.somnus.server.backend.config.DatabaseConnection;

import java.util.List;

// TODO check if possible
public abstract class ObjectDatabaseConnection<T> {
    private static final MongoDatabase database = DatabaseConnection.getMongoDatabase();
    private static final String articleCollectionName = "articles";
    private MongoCollection collection;

    /**
     * Gets the first 20 Objects. The purpose is to save
     * bandwidth instead of retrieving all objects at once
     */
    public List<T> getFirst20() {
        return null;
    }

    /**
     * Gets all objects from database
     * @return - list of all objects
     */
    public List<T> getAllObject() {
        FindIterable<T> allObjects = collection.find();
        return null;
    }

    /**
     * Gets one object matching the given id from the
     * database
     * @param id - id to match to
     * @return - Object matching the id
     */
    public T getObject(String id) {

        return null;
    }

    /**
     * Delete an object from the database
     * @param id - Id of the object to delete
     */
    public void deleteObject(String id) {

    }
}
