package com.somnus.server.backend.articles.repository;

import com.somnus.server.backend.articles.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.articleId = :articleId")
    List<Comment> getCommentsByArticleId(Integer articleId);
}
