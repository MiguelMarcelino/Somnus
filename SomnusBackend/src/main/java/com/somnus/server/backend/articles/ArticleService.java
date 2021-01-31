package com.somnus.server.backend.articles;

import com.somnus.server.backend.articles.database.ArticleDatabaseConnection;
import com.somnus.server.backend.articles.domain.Article;
import com.somnus.server.backend.articles.domain.ArticleTopic;
import com.somnus.server.backend.articles.dto.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    public ArticleDatabaseConnection articleDatabaseConnection;

    public List<ArticleDto> getAllArticles() {
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleDatabaseConnection.getAllObject().forEach(article -> articleDtos.add(
                new ArticleDto(article.getArticleName(), article.getAuthorUserName(),
                        article.getDescription(), article.getDatePublished(),
                        article.getTopic().name, article.getContent())));
        return articleDtos;
    }

    public List<ArticleDto> getArticlesOfTopic(String articleTopic) {
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleDatabaseConnection.getArticlesMatchingTopic(articleTopic).forEach(article -> articleDtos.add(
                new ArticleDto(article.getArticleName(), article.getAuthorUserName(),
                        article.getDescription(), article.getDatePublished(),
                        article.getTopic().name, article.getContent())));
        return articleDtos;
    }

    public ArticleDto getArticle(String id) {
        Article article = articleDatabaseConnection.getObject(id);
        return new ArticleDto(article.getArticleName(), article.getAuthorUserName(),
                article.getDescription(), article.getDatePublished(),
                article.getTopic().name, article.getContent());
    }

    public void createArticle(ArticleDto articleDto) {
        Article article = new Article(articleDto.getArticleName(),
                articleDto.getAuthorUserName(), articleDto.getDescription(),
                ArticleTopic.valueOf(articleDto.getTopic()), articleDto.getContent());
        articleDatabaseConnection.createObject(article);
    }

    public void deleteArticle(String id) {
        articleDatabaseConnection.deleteObject(id);
    }

    public List<ArticleDto> searchArticles(String nameContent) {
        // TODO: Check for any articles in the database containing the string
        //  content within the name
        return null;
    }
}
