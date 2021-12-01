package com.somnus.server.postservice.articleComments.repository;

import com.somnus.server.postservice.articleComments.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.articleId = :articleId")
    List<Comment> getCommentsByArticleId(Integer articleId);

    @Query("SELECT c FROM Comment c WHERE c.username = :username")
    List<Comment> getCommentsByUsername(String username);

    @Query("DELETE FROM Comment WHERE id=:commentId")
    @Modifying
    void removeCommentById(Integer commentId);

    @Query("SELECT c FROM Comment c WHERE c.parentComment = :comment")
    List<Comment> getCommentsWithParentId(Comment comment);
}
