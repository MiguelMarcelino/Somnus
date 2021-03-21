package com.somnus.server.backend.articleComments;

import com.somnus.server.backend.articles.domain.Article;
import com.somnus.server.backend.articleComments.domain.Comment;
import com.somnus.server.backend.articleComments.dto.CommentDto;
import com.somnus.server.backend.articles.repository.ArticleRepository;
import com.somnus.server.backend.articleComments.repository.CommentRepository;
import com.somnus.server.backend.config.DateHandler;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.notifications.config.PusherInfo;
import com.somnus.server.backend.notifications.handlers.NotificationHandler;
import com.somnus.server.backend.users.domain.Role;
import com.somnus.server.backend.users.domain.User;
import com.somnus.server.backend.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationHandler notificationHandler;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CommentDto> getCommentsFromArticle(User user, Integer articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }

        List<Comment> comments = commentRepository.getCommentsByArticleId(articleId);
        return getCommentDtos(comments, user);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CommentDto addCommentToArticle(User user, CommentDto commentDto) {
        Optional<Article> optionalArticle = articleRepository.findById(commentDto.getArticleId());
        if (optionalArticle.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }

        Comment comment = new Comment(commentDto.getArticleId(), user.getUsername(),
                user.getDisplayName(), commentDto.getContent());

        commentRepository.save(comment);
        if (commentDto.getParentId() != null) {
            Optional<Comment> optionalComment = commentRepository.findById(commentDto.getParentId());
            if (optionalComment.isEmpty()) {
                throw new SomnusException(ErrorMessage.NO_COMMENT_FOUND);
            }
            Comment parentComment = optionalComment.get();
            parentComment.addResponseComment(comment);
            comment.setParentComment(parentComment);
            commentRepository.save(comment);
            commentRepository.save(parentComment);
        }

        CommentDto resultComment = createCommentDto(comment, user);

        if (!comment.getResponseComments().isEmpty()) {
            List<CommentDto> responseComments = getCommentDtos(comment.getResponseComments(), user);
            resultComment.setResponseComments(responseComments);
        }

        this.notificationHandler.sendNotification(PusherInfo.ARTICLE_CHANNEL, PusherInfo.COMMENT_PUBLISHED_EVENT,
                resultComment);

        return resultComment;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
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
        if (!comment.getUsername().equals(user.getUsername())) {
            throw new SomnusException(ErrorMessage.COMMENT_EDIT_NOT_ALLOWED);
        }

        // authorized, so allow editing
        comment.setContent(commentDto.getContent());
        comment.setEditedAt(DateHandler.now());
        commentRepository.save(comment);

        CommentDto editedComment = createCommentDto(comment, user);
        return editedComment;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addCommentLike(User user, Integer commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_COMMENT_FOUND);
        }

        Comment comment = optionalComment.get();
        // temporary fix
        User commentUser = userRepository.findByUserName(user.getUsername());
        boolean userLike = comment.getUserLikes().containsKey(user.getId());
        if (!userLike) {
            comment.setNumLikes(comment.getNumLikes() + 1);
            comment.addUserLikes(commentUser);
            commentRepository.save(comment);
        }
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void removeCommentLike(User user, Integer commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_COMMENT_FOUND);
        }

        Comment comment = optionalComment.get();
        // temporary fix
        User commentUser = userRepository.findByUserName(user.getUsername());
        boolean userLike = comment.getUserLikes().containsKey(user.getId());
        if (userLike) {
            comment.setNumLikes(comment.getNumLikes() - 1);
            comment.removeUserLike(commentUser);
            commentRepository.save(comment);
        }
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void removeComment(User user, int commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_COMMENT_FOUND);
        }
        Comment comment = optionalComment.get();
        if(!comment.getUsername().equals(user.getUsername()) && !user.getRole().equals(Role.ADMIN)) {
            throw new SomnusException(ErrorMessage.DELETE_COMMENT_NOT_ALLOWED);
        }

        System.out.println(commentId);
        // authorized, so remove comment and its children
        this.commentRepository.removeCommentById(commentId);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// Private Methods ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private List<CommentDto> getCommentDtos(List<Comment> comments, User user) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        comments.forEach(comment -> commentDtoList.add(createCommentDto(comment, user)));
        return commentDtoList;
    }

    private CommentDto createCommentDto(Comment comment, User user) {
        Comment parentComment = comment.getParentComment();
        Integer parentId = null;
        if (parentComment != null) {
            parentId = parentComment.getId();
        }
        CommentDto commentDto = new CommentDto(comment.getId(), comment.getArticleId(), comment.getUsername(),
                comment.getUserDisplayName(), comment.getPublishedAt(), comment.getEditedAt(),
                comment.getContent(), comment.getNumLikes(), parentId);

        if(user != null) {
            boolean userLike = comment.getUserLikes().containsKey(user.getId());
            commentDto.setUserLikedComment(userLike);
        }

        return commentDto;
    }

}
