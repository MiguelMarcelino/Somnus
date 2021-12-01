package com.somnus.server.postservice.articles;

import com.somnus.server.postservice.articles.domain.Article;
import com.somnus.server.postservice.articles.dto.ArticleDto;
import com.somnus.server.postservice.articles.repository.ArticleRepository;
import com.somnus.server.postservice.exceptions.ErrorMessage;
import com.somnus.server.postservice.exceptions.SomnusException;
import com.somnus.server.postservice.post.PostService;
import com.somnus.server.postservice.topic.ArticleTopic;
import com.somnus.server.postservice.user.Role;
import com.somnus.server.postservice.user.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PostService postService;

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ArticleDto> getAllNonDeletedArticles() {
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleRepository.findAllNonDeletedArticles().forEach(article -> articleDtos.add(
                createArticleDto(article)));
        return articleDtos;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ArticleDto> getArticlesOfTopic(String articleTopic) {
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleRepository.getArticlesMatchingTopic(articleTopic).forEach(article -> articleDtos.add(
                createArticleDto(article)));
        return articleDtos;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ArticleDto getNonDeletedArticle(Integer id) {
        Optional<Article> optionalArticle = articleRepository.findByIdAndIsDeletedNot(id);
        if (optionalArticle.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }

        Article article = optionalArticle.get();
        return createArticleDto(article);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createOrUpdateArticle(User user, ArticleDto articleDto) {
        // Parse article Topic
        if (Strings.isBlank(articleDto.getTopic())) {
            throw new SomnusException(ErrorMessage.NO_TOPIC_PROVIDED);
        }

        Article article = null;
        if (articleDto.getId() != null) {
            article = articleRepository.getById(Integer.parseInt(articleDto.getId()));
        }

        postService.postCreateAuthCheck(user);

        ArticleTopic articleTopic = ArticleTopic.valueOf(articleDto.getTopic()
                .replace(" ", "_").toUpperCase());
        if (article == null) {
            article = new Article(user.getId(), articleDto.getPostName(),
                    articleDto.getAuthorUserName(), user.getFirebaseGeneratedUserID(),
                    articleDto.getDescription(), articleTopic, articleDto.getContent());
        } else {
            article.setPostName(articleDto.getPostName());
            article.setDescription(articleDto.getDescription());
            article.setTopic(articleTopic);
            article.setContent(articleDto.getContent());
            article.updateLastUpdate();
        }
        article.setIsDeleted(articleDto.isDeleted());
        articleRepository.save(article);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteArticle(User user, Integer id) {
        Article article = articleRepository.getById(id);

        // Article can only be deleted by the person who wrote it or the admin
        if (!article.getAuthorUserName().equals(user.getFirebaseGeneratedUserID()) &&
                !user.getRole().equals(Role.ADMIN)) {
            throw new SomnusException(ErrorMessage.DELETE_ARTICLE_NOT_ALLOWED);
        }

        article.setIsDeleted(true);
        articleRepository.save(article);
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
        articles.forEach(article -> articleDtos.add(createArticleDto(article)));
        return articleDtos;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ArticleDto> getDeletedArticles(User user) {
        List<Article> articles = articleRepository
                .getAllDeletedArticlesFromAuthorUsername(user.getFirebaseGeneratedUserID());

        List<ArticleDto> articleDtos = new ArrayList<>();
        articles.forEach(post -> articleDtos.add(createArticleDto(post)));
        return articleDtos;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void restoreArticle(User user, Integer id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }
        Article article = optionalArticle.get();

        postService.postCreateAuthCheck(user);
        article.setIsDeleted(false);
        articleRepository.save(article);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ArticleDto getDeletedArticle(User user, Integer id) {
        Optional<Article> optionalArticle = articleRepository.findByIdAndIsDeleted(id);
        if(optionalArticle.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }

        Article article = optionalArticle.get();
        if(!Objects.equals(user.getId(), article.getAuthorId())) {
            throw new SomnusException(ErrorMessage.ARTICLE_GET_NOT_AUTHORIZED);
        }
        return createArticleDto(article);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// Private Methods ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private ArticleDto createArticleDto(Article article) {
        return new ArticleDto(String.valueOf(article.getId()), article.getPostName(),
                postService.normalizeName(article.getPostName()), article.getAuthorUserName(),
                article.getFirebaseGeneratedUserID(), article.getDescription(), article.getDatePublished(),
                article.getLastUpdate(), article.getTopic().name, postService.normalizeName(article.getTopic().name),
                article.getIsDeleted(), article.getContent());
    }

}
