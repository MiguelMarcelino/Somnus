package com.somnus.server.backend.articles;

import com.somnus.server.backend.articles.database.ArticleDatabaseConnection;
import com.somnus.server.backend.articles.domain.Article;
import com.somnus.server.backend.articles.domain.ArticleTopic;
import com.somnus.server.backend.articles.dto.ArticleDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    public ArticleDatabaseConnection articleDatabaseConnection;

    public ArticleService() {
        articleDatabaseConnection = new ArticleDatabaseConnection();
    }

    public List<ArticleDto> getAllArticles() {
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleDatabaseConnection.getAllArticles().forEach(article -> articleDtos.add(
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
        Article article = articleDatabaseConnection.getArticle(id);
        return new ArticleDto(article.getArticleName(), article.getAuthorUserName(),
                article.getDescription(), article.getDatePublished(),
                article.getTopic().name, article.getContent());
    }

    public void createArticle(ArticleDto articleDto) {
        Article article = new Article(articleDto.getArticleName(),
                articleDto.getAuthorUserName(), articleDto.getDescription(),
                ArticleTopic.valueOf(articleDto.getTopic()), articleDto.getContent());
        articleDatabaseConnection.createArticle(article);
    }

    public void deleteArticle(String id) {
        articleDatabaseConnection.deleteArticle(id);
    }

    public List<ArticleDto> searchArticles(String nameContent) {
        // TODO: Check for any articles in the database containing the string
        //  content within the name
        return null;
    }
}
