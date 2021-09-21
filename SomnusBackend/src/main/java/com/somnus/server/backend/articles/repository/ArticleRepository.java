package com.somnus.server.backend.articles.repository;


import com.somnus.server.backend.articles.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query("SELECT a FROM Article a WHERE a.isDeleted = false")
    List<Article> findAllNonDeletedArticles();

    @Query("SELECT a FROM Article a WHERE a.topic = :topic AND a.isDeleted = false")
    List<Article> getArticlesMatchingTopic(String topic);

    @Query("SELECT a FROM Article a WHERE a.postName LIKE :nameContent AND a.isDeleted = false")
    List<Article> getAllArticlesContainingSubstring(String nameContent);

    @Query("SELECT a FROM Article a WHERE a.isDeleted = true AND a.author.username LIKE :authorUserName")
    List<Article> getAllDeletedArticlesFromAuthorUsername(String authorUserName);

    @Query("SELECT a FROM Article a WHERE a.id = :id AND a.isDeleted = false")
    Optional<Article> findByIdAndIsDeletedNot(Integer id);

    @Query("SELECT a FROM Article a WHERE a.id = :id AND a.isDeleted = true")
    Optional<Article> findByIdAndIsDeleted(Integer id);
}
