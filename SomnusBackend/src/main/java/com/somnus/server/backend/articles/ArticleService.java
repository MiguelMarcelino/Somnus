package com.somnus.server.backend.articles;

import com.mongodb.client.MongoClient;
import com.somnus.server.backend.articles.domain.ArticleTopic;
import com.somnus.server.backend.articles.dto.ArticleDto;
import com.somnus.server.backend.config.DatabaseConnection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    public List<ArticleDto> getArticles(ArticleTopic articleTopic) {
        // TODO
        MongoClient mongoClient = DatabaseConnection.getMongoClient();
        return null;
    }
}
