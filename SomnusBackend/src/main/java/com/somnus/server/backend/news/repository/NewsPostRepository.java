package com.somnus.server.backend.news.repository;

import com.somnus.server.backend.news.domain.NewsPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NewsPostRepository extends JpaRepository<NewsPost, Integer> {

    @Query("SELECT a FROM NewsPost a WHERE a.isDeleted = false")
    List<NewsPost> findAllNonDeletedNewsPosts();

    @Query("SELECT a FROM NewsPost a WHERE a.isDeleted = true AND a.author.username = :authorUserName")
    List<NewsPost> getAllDeletedNewsPostsFromAuthorUsername(String authorUserName);
}
