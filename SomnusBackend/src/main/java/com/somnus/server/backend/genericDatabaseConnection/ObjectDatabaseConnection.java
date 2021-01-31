package com.somnus.server.backend.genericDatabaseConnection;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.somnus.server.backend.config.DatabaseConnection;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import org.bson.Document;
import org.springframework.core.GenericTypeResolver;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjectDatabaseConnection<T> {
    // TODO check if possible
    private Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), ObjectDatabaseConnection.class);;

    private static final MongoDatabase database = DatabaseConnection.getMongoDatabase();
    protected MongoCollection<T> collection;

    public ObjectDatabaseConnection(String collectionName) {
        collection = database.getCollection(collectionName, genericType);
    }

    /**
     * Gets the first 20 Objects. The purpose is to save
     * bandwidth instead of retrieving all objects at once
     */
    public List<T> getFirst20() {
        // TODO
        return null;
    }

    /**
     * Gets all objects from database
     * @return - list of all objects
     */
    public List<T> getAllObject() {
        List<T> objects = new ArrayList<>();
        FindIterable<T> allObjects = collection.find();
        for (T object : allObjects) {
            objects.add(object);
        }
        return objects;
    }

    /**
     * Gets one object matching the given id from the
     * database
     * @param id - id to match to
     * @return - Object matching the id
     */
    public T getObject(String id) {
        T object = collection.find(new Document("_id", id)).first();
        if(object != null) {
            return object;
        }
        throw new SomnusException(ErrorMessage.NO_OBJECT_FOUND, genericType.getClass().toString());
    }

    /**
     * Adds a new object to the database
     * @param object - object to add
     */
    public void createObject(T object) {
        if(!collection.insertOne(object).wasAcknowledged()){
            throw new SomnusException(ErrorMessage.OBJECT_INSERT_FAILED, genericType.getClass().toString());
        }
    }

    /**
     * Delete an object from the database
     * @param id - Id of the object to delete
     */
    public void deleteObject(String id) {
        long deleted = collection.deleteOne(new Document("_id", id)).getDeletedCount();
        if(deleted == 0) {
            throw new SomnusException(ErrorMessage.NO_OBJECTS_TO_DELETE, genericType.getClass().toString());
        }
    }
}
