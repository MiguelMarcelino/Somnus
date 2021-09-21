package com.somnus.server.backend.news.repository;

import com.somnus.server.backend.articles.domain.Article;
import com.somnus.server.backend.news.domain.NewsPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DeletedNewsPostRepository extends JpaRepository<NewsPost, Integer> {

    @Query("SELECT a FROM NewsPost a WHERE a.authorUserName = :authorUserName")
    List<NewsPost> getAllDeletedNewsPostsFromAuthorUsername(String authorUserName);
}
