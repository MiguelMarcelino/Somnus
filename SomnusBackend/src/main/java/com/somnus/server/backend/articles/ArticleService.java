package com.somnus.server.backend.articles;

import com.somnus.server.backend.articles.repository.ArticleRepository;
import com.somnus.server.backend.articles.domain.Article;
import com.somnus.server.backend.articles.domain.ArticleTopic;
import com.somnus.server.backend.articles.dto.ArticleDto;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ArticleDto> getAllArticles() {
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleRepository.findAll().forEach(article -> articleDtos.add(
                new ArticleDto(article.getId(), article.getArticleName(), article.getAuthorUserName(),
                        article.getDescription(), article.getDatePublished(),
                        article.getTopic().name, article.getContent())));
        return articleDtos;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ArticleDto> getArticlesOfTopic(String articleTopic) {
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleRepository.getArticlesMatchingTopic(articleTopic).forEach(article -> articleDtos.add(
                new ArticleDto(article.getId(), article.getArticleName(), article.getAuthorUserName(),
                        article.getDescription(), article.getDatePublished(),
                        article.getTopic().name, article.getContent())));
        return articleDtos;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ArticleDto getArticle(Integer id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if(!optionalArticle.isPresent()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }

        Article article = optionalArticle.get();
        return new ArticleDto(article.getId(), article.getArticleName(), article.getAuthorUserName(),
                article.getDescription(), article.getDatePublished(),
                article.getTopic().name, article.getContent());
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createArticle(ArticleDto articleDto) {
        Article article = new Article(articleDto.getArticleName(),
                articleDto.getAuthorUserName(), articleDto.getDescription(),
                ArticleTopic.valueOf(articleDto.getTopic().toUpperCase()), articleDto.getContent());
        articleRepository.save(article);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ArticleDto> searchArticles(String nameContent) {
        // TODO: Find more efficient search method
        String searchQuery = "%" + nameContent + "%";
        List<Article> articles = articleRepository.getAllArticlesContainingSubstring(searchQuery);
        List<ArticleDto> articleDtos = new ArrayList<>();
        articles.forEach(article -> articleDtos.add(new ArticleDto(article.getId(), article.getArticleName(),
                article.getAuthorUserName(), article.getDescription(), article.getDatePublished(),
                article.getTopic().name, article.getContent())));
        return articleDtos;
    }
}
