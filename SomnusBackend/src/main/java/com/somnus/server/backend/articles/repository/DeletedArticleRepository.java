package com.somnus.server.backend.articles.repository;

import com.somnus.server.backend.articles.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DeletedArticleRepository extends JpaRepository<Article, Integer> {

    @Query("SELECT a FROM Article a WHERE a.authorUserName = :authorUserName")
    List<Article> getAllDeletedArticlesFromAuthorUsername(String authorUserName);
}
