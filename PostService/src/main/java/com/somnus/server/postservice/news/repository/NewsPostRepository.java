package com.somnus.server.postservice.news.repository;

import com.somnus.server.postservice.news.domain.NewsPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface NewsPostRepository extends JpaRepository<NewsPost, Integer> {
}
