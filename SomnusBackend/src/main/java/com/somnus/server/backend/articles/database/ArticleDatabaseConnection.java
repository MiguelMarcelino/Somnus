package com.somnus.server.backend.articles.database;

import com.somnus.server.backend.articles.domain.Article;
import com.somnus.server.backend.genericDatabaseConnection.ObjectDatabaseConnection;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleDatabaseConnection extends ObjectDatabaseConnection<Article> {
    private static final String articleCollectionName = "articles";

    public ArticleDatabaseConnection() {
        super(articleCollectionName);
    }

    /**
     * Gets all articles matching a certain topic
     * @return List of articles matching a topic
     */
    public List<Article> getArticlesMatchingTopic(String topicName) {
        List<Article> articles = new ArrayList<>();

        // search for all articles with a given topic
        this.collection.find(new Document("topic", topicName));

        for (Article article : collection.find()) {
            articles.add(article);
        }
        return articles;
    }

}
