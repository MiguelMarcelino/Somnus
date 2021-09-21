package com.somnus.server.backend.news;

import com.somnus.server.backend.articles.dto.ArticleDto;
import com.somnus.server.backend.news.dto.NewsPostDTO;
import com.somnus.server.backend.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/news-api")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news-posts")
    public List<NewsPostDTO> getNewsPosts() {
        return newsService.getAllNews();
    }

    @GetMapping(value = "/deleted-news-posts")
    public List<NewsPostDTO> getDeletedNewsPosts(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();

        return newsService.getDeletedNewsPosts(user);
    }

    @GetMapping(value = "/restore-news-post/{id}")
    public void restoreArticle(Principal principal, @PathVariable Integer id) {
        User user = (User) ((Authentication) principal).getPrincipal();

        newsService.restoreNewsPost(user, id);
    }

    @GetMapping(value = "/news-post/{id}")
    public NewsPostDTO getNewsPostById(@PathVariable Integer id) {
        return newsService.getPostWithID(id);
    }

    @DeleteMapping(value = "/news-post/delete/{id}")
    public void deleteArticle(Principal principal, @PathVariable Integer id) {
        User user = (User) ((Authentication) principal).getPrincipal();

        newsService.deleteNewsPost(user, id);
    }

    @PostMapping(value = "/news-post/create")
    public void createOrUpdateNewsPost(Principal principal, @RequestBody NewsPostDTO newsPostDTO) {
        User user = (User) ((Authentication) principal).getPrincipal();

        newsService.createOrUpdateNewsPost(user, newsPostDTO);
    }

}
