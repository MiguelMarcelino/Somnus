package com.somnus.server.postservice.news;

import com.somnus.server.postservice.exceptions.ErrorMessage;
import com.somnus.server.postservice.exceptions.SomnusException;
import com.somnus.server.postservice.news.domain.NewsPost;
import com.somnus.server.postservice.news.dto.NewsPostDTO;
import com.somnus.server.postservice.news.repository.NewsPostRepository;
import com.somnus.server.postservice.post.PostService;
import com.somnus.server.postservice.user.Role;
import com.somnus.server.postservice.user.User;
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

    public List<NewsPostDTO> getAllNews() {
        List<NewsPost> newsPosts = newsPostRepository.findAll();
        List<NewsPostDTO> newsPostDTOS = new ArrayList<>();
        newsPosts.forEach(post -> newsPostDTOS.add(createNewsPostDTO(post)));
        return newsPostDTOS;
    }


    public NewsPostDTO getPostWithID(Integer id) {
        Optional<NewsPost> newsPostOpt = newsPostRepository.findByIdAndIsDeletedNot(id);
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
        if (!newsPost.getAuthorId().equals(user.getId()) &&
                !user.getRole().equals(Role.ADMIN)) {
            throw new SomnusException(ErrorMessage.DELETE_NEWS_POST_NOT_ALLOWED);
        }

        newsPost.setIsDeleted(true);
        newsPostRepository.save(newsPost);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<NewsPostDTO> getDeletedNewsPosts(User user) {
        List<NewsPost> newsPosts = newsPostRepository.
                getAllDeletedNewsPostsFromAuthorUsername(user.getFirebaseGeneratedUserID());

        List<NewsPostDTO> newsPostDTOs = new ArrayList<>();
        newsPosts.forEach(post -> newsPostDTOs.add(createNewsPostDTO(post)));
        return newsPostDTOs;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void restoreNewsPost(User user, Integer id) {
        Optional<NewsPost> optionalNewsPost = newsPostRepository.findById(id);
        if (optionalNewsPost.isEmpty()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }
        NewsPost newsPost = optionalNewsPost.get();

        postService.postCreateAuthCheck(user);
        newsPost.setIsDeleted(false);
        newsPostRepository.save(newsPost);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public NewsPostDTO getDeletedNewsPost(User user, Integer id) {
        Optional<NewsPost> optionalArticle = newsPostRepository.findByIdAndIsDeleted(id);
        if(!optionalArticle.isPresent()) {
            throw new SomnusException(ErrorMessage.NO_ARTICLE_FOUND);
        }

        NewsPost newsPost = optionalArticle.get();
        if(user.getId() != newsPost.getAuthorId()) {
            throw new SomnusException(ErrorMessage.ARTICLE_GET_NOT_AUTHORIZED);
        }
        NewsPostDTO newsPostDTO = createNewsPostDTO(newsPost);
        return newsPostDTO;
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
            newsPost = new NewsPost(user.getId(), newsPostDTO.getPostName(),
                    newsPostDTO.getAuthorUserName(), user.getFirebaseGeneratedUserID(),
                    newsPostDTO.getDescription(), newsPostDTO.getContent());
        } else {
            newsPost.setPostName(newsPostDTO.getPostName());
            newsPost.setDescription(newsPostDTO.getDescription());
            newsPost.setContent(newsPostDTO.getContent());
            newsPost.updateLastUpdate();
        }
        newsPost.setIsDeleted(newsPostDTO.isDeleted());
        newsPostRepository.save(newsPost);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// Private Methods ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private NewsPostDTO createNewsPostDTO(NewsPost newsPost) {
        return new NewsPostDTO(String.valueOf(newsPost.getId()), newsPost.getPostName(),
                postService.normalizeName(newsPost.getPostName()), newsPost.getAuthorUserName(),
                newsPost.getAuthorUserName(), newsPost.getDescription(), newsPost.getDatePublished(),
                newsPost.getLastUpdate(), newsPost.getIsDeleted(), newsPost.getContent());
    }
}
