package com.somnus.server.backend.news;

import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.news.domain.NewsPost;
import com.somnus.server.backend.news.dto.NewsPostDTO;
import com.somnus.server.backend.news.repository.NewsPostRepository;
import com.somnus.server.backend.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<NewsPost> newsPostOpt = newsPostRepository.findById(id);
        if(!newsPostOpt.isPresent()) {
            throw new SomnusException(ErrorMessage.NO_NEWS_POST_FOUND);
        }

        NewsPostDTO newsPostDTO = createNewsPostDTO(newsPostOpt.get());
        return newsPostDTO;
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
