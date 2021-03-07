package com.somnus.server.backend.articles;

import com.somnus.server.backend.articles.domain.Article;
import com.somnus.server.backend.articles.domain.ArticleTopic;
import com.somnus.server.backend.articles.domain.Comment;
import com.somnus.server.backend.articles.dto.ArticleDto;
import com.somnus.server.backend.articles.dto.CommentDto;
import com.somnus.server.backend.articles.repository.ArticleRepository;
import com.somnus.server.backend.articles.repository.CommentRepository;
import com.somnus.server.backend.config.DateHandler;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.notifications.config.PusherConfig;
import com.somnus.server.backend.notifications.config.PusherInfo;
import com.somnus.server.backend.notifications.handlers.NotificationHandler;
import com.somnus.server.backend.users.RolesHandler;
import com.somnus.server.backend.users.domain.Role;
import com.somnus.server.backend.users.domain.User;
import com.somnus.server.backend.users.dto.UserDto;
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
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RolesHandler rolesHandler;

    @Autowired
    private NotificationHandler notificationHandler;

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ArticleDto> getAllArticles() {
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleRepository.findAll().forEach(article -> articleDtos.add(
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
    public ArticleDto getArticle(Integer id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
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
            article = articleRepository.getOne(Integer.parseInt(articleDto.getId()));
        }

        ArticleTopic articleTopic = ArticleTopic.valueOf(articleDto.getTopic()
                .replace(" ", "_").toUpperCase());
        if (article == null) {
            article = new Article(user, articleDto.getArticleName(),
                    articleDto.getAuthorUserName(), articleDto.getDescription(),
                    articleTopic, articleDto.getContent());
        } else {
            article.setArticleName(articleDto.getArticleName());
            article.setDescription(articleDto.getDescription());
            article.setTopic(articleTopic);
            article.setContent(articleDto.getContent());
            article.updateLastUpdate();
        }
        articleRepository.save(article);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteArticle(User user, Integer id) {
        Article article = articleRepository.getOne(id);

        // Article can only be deleted by the person who wrote it or the admin
        if (!article.getAuthor().getUsername().equals(user.getUsername()) &&
                !user.getRole().equals(Role.ADMIN)) {
            throw new SomnusException(ErrorMessage.DELETE_ARTICLE_NOT_ALLOWED);
        }

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
        articles.forEach(article -> articleDtos.add(createArticleDto(article)));
        return articleDtos;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////// Article Comments /////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<CommentDto> getCommentsFromArticle(Integer articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }

        List<Comment> comments = commentRepository.getCommentsByArticleId(articleId);
        return parseResponseComments(comments);
    }

    public CommentDto addCommentToArticle(User user, CommentDto commentDto) {
        Optional<Article> optionalArticle = articleRepository.findById(commentDto.getArticleId());
        if (optionalArticle.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }

        Comment comment = new Comment(commentDto.getArticleId(), user,
                commentDto.getContent());
        commentRepository.save(comment);

        if (commentDto.getParentId() != null) {
            Optional<Comment> optionalComment = commentRepository.findById(commentDto.getParentId());
            if (optionalComment.isEmpty()) {
                throw new SomnusException(ErrorMessage.NO_COMMENT_FOUND);
            }
            Comment parentComment = optionalComment.get();
            parentComment.addResponseComment(comment);
            commentRepository.save(parentComment);
        }

        UserDto userDto = getUserDto(user);
        CommentDto resultComment = createCommentDto(comment);

        if (!comment.getResponseComments().isEmpty()) {
            List<CommentDto> responseComments = parseResponseComments(comment.getResponseComments());
            resultComment.setResponseComments(responseComments);
        }

        this.notificationHandler.sendNotification(PusherInfo.ARTICLE_CHANNEL, PusherInfo.COMMENT_PUBLISHED_EVENT,
                resultComment);

        return resultComment;
    }

    public CommentDto updateComment(User user, CommentDto commentDto) {
        Optional<Article> optionalArticle = articleRepository.findById(commentDto.getArticleId());
        if (optionalArticle.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }

        Optional<Comment> optionalComment = commentRepository.findById(commentDto.getId());
        if (optionalComment.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_COMMENT_FOUND);
        }

        Comment comment = optionalComment.get();
        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new SomnusException(ErrorMessage.COMMENT_EDIT_NOT_ALLOWED);
        }

        // authorized, so allow editing
        comment.setContent(commentDto.getContent());
        comment.setEditedAt(DateHandler.now());
        commentRepository.save(comment);

        CommentDto editedComment = createCommentDto(comment);
        return editedComment;
    }

    public void addCommentLike(User user, Integer commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_COMMENT_FOUND);
        }

        Comment comment = optionalComment.get();
        comment.setNumLikes(comment.getNumLikes() + 1);
        commentRepository.save(comment);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// Private Methods ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private ArticleDto createArticleDto(Article article) {
        return new ArticleDto(String.valueOf(article.getId()), article.getArticleName(),
                article.getAuthorUserName(), article.getAuthor().getUsername(), article.getDescription(),
                article.getDatePublished(), article.getLastUpdate(), article.getTopic().name, article.getContent());
    }

    private CommentDto createCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getArticleId(), getUserDto(comment.getUser()),
                comment.getPublishedAt(), comment.getEditedAt(), comment.getContent(),
                comment.getNumLikes());
    }

    private UserDto getUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(),
                user.getDisplayName(), user.getFirstName(), user.getLastName(),
                user.getRole().name, user.getPhotoURL());
    }

    private List<CommentDto> parseResponseComments(List<Comment> comments) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        comments.forEach(comment ->
                commentDtoList.add(createCommentDto(comment)));
        return commentDtoList;
    }
}
