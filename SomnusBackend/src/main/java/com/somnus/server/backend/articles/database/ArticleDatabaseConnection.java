package com.somnus.server.backend.articles.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.somnus.server.backend.articles.domain.Article;
import com.somnus.server.backend.config.DatabaseConnection;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ArticleDatabaseConnection {
    private static final MongoDatabase database = DatabaseConnection.getMongoDatabase();
    private static final String articleCollectionName = "articles";
    private MongoCollection collection;

    public ArticleDatabaseConnection() {
        collection = database.getCollection(articleCollectionName, Article.class);
    }

    /**
     * Gets all articles from database
     * @return - list of all articles
     */
    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        for (Object article : collection.find()) {
            if(article instanceof Article) {
                articles.add(((Article) article));
            }
        }
        return articles;
    }

    /**
     * Gets all articles matching a certain topic
     * @return List of articles matching a topic
     */
    public List<Article> getArticlesMatchingTopic(String topic) {
        List<Article> articles = new ArrayList<>();

        // search for all articles with a given topic
        collection.find(new Document("topic", topic));

        for (Object article : collection.find()) {
            if(article instanceof Article) {
                articles.add(((Article) article));
            }
        }
        return articles;
    }

    public Article getArticle(String id) {
        Object object = collection.find(new Document("_id", id)).first();
        if(object != null && object instanceof Article) {
            return ((Article) object);
        }
        throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
    }

    public void createArticle(Article article) {
        if(!collection.insertOne(article).wasAcknowledged()){
            throw new SomnusException(ErrorMessage.ARTICLE_INSERT_FAILED);
        }
    }

    public void deleteArticle(String id) {
        long deleted = collection.deleteOne(new Document("_id", id)).getDeletedCount();
        if(deleted == 0) {
            throw new SomnusException(ErrorMessage.NO_ARTICLES_TO_DELETE);
        }
    }
}
