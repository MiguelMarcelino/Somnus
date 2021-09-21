package com.somnus.server.backend.news;

import com.somnus.server.backend.articles.domain.Article;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.news.domain.NewsPost;
import com.somnus.server.backend.news.dto.NewsPostDTO;
import com.somnus.server.backend.news.repository.DeletedNewsPostRepository;
import com.somnus.server.backend.news.repository.NewsPostRepository;
import com.somnus.server.backend.post.PostService;
import com.somnus.server.backend.users.domain.Role;
import com.somnus.server.backend.users.domain.User;
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
public class NewsService {

    @Autowired
    private NewsPostRepository newsPostRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private DeletedNewsPostRepository deletedNewsPostRepository;

    public List<NewsPostDTO> getAllNews() {
        List<NewsPost> newsPosts = newsPostRepository.findAll();
        List<NewsPostDTO> newsPostDTOS = new ArrayList<>();
        newsPosts.forEach(post -> newsPostDTOS.add(createNewsPostDTO(post)));
        return newsPostDTOS;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<NewsPostDTO> getDeletedNewsPosts(User user) {
        List<NewsPost> newsPosts = deletedNewsPostRepository.getAllDeletedNewsPostsFromAuthorUsername(user.getUsername());
        System.out.println(user.getUsername());
        if (!newsPosts.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_NEWS_POST_FOUND);
        }

        List<NewsPostDTO> newsPostDTOs = new ArrayList<>();
        newsPosts.forEach(post -> newsPostDTOs.add(createNewsPostDTO(post)));
        return newsPostDTOs;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void restoreNewsPost(User user, Integer id) {
        Optional<NewsPost> optionalNewsPost = deletedNewsPostRepository.findById(id);
        if (optionalNewsPost.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }
        NewsPost newsPost = optionalNewsPost.get();

        postService.postCreateAuthCheck(user);
        newsPostRepository.save(newsPost);
        deletedNewsPostRepository.delete(newsPost);
    }

    public NewsPostDTO getPostWithID(Integer id) {
        Optional<NewsPost> newsPostOpt = newsPostRepository.findById(id);
        if (!newsPostOpt.isPresent()) {
            throw new SomnusException(ErrorMessage.NO_NEWS_POST_FOUND);
        }

        NewsPostDTO newsPostDTO = createNewsPostDTO(newsPostOpt.get());
        return newsPostDTO;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteNewsPost(User user, Integer id) {
        NewsPost newsPost = newsPostRepository.getOne(id);

        // News Post can only be deleted by the person who wrote it or the admin
        if (!newsPost.getAuthor().getUsername().equals(user.getUsername()) &&
                !user.getRole().equals(Role.ADMIN)) {
            throw new SomnusException(ErrorMessage.DELETE_NEWS_POST_NOT_ALLOWED);
        }

        deletedNewsPostRepository.save(newsPost);
        newsPostRepository.deleteById(id);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createOrUpdateNewsPost(User user, NewsPostDTO newsPostDTO) {
        NewsPost newsPost = null;
        if (newsPostDTO.getId() != null) {
            newsPost = newsPostRepository.getOne(Integer.parseInt(newsPostDTO.getId()));
        }

        postService.postCreateAuthCheck(user);

        if (newsPost == null) {
            newsPost = new NewsPost(user, newsPostDTO.getPostName(),
                    newsPostDTO.getAuthorUserName(), newsPostDTO.getDescription(),
                    newsPostDTO.getContent());
        } else {
            newsPost.setPostName(newsPostDTO.getPostName());
            newsPost.setDescription(newsPostDTO.getDescription());
            newsPost.setContent(newsPostDTO.getContent());
            newsPost.updateLastUpdate();
        }
        newsPostRepository.save(newsPost);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// Private Methods ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private NewsPostDTO createNewsPostDTO(NewsPost newsPost) {
        return new NewsPostDTO(String.valueOf(newsPost.getId()), newsPost.getPostName(),
                postService.normalizeName(newsPost.getPostName()), newsPost.getAuthorUserName(),
                newsPost.getAuthor().getUsername(), newsPost.getDescription(), newsPost.getDatePublished(),
                newsPost.getLastUpdate(), newsPost.getContent());
    }
}
