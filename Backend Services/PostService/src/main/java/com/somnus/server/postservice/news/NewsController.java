package com.somnus.server.postservice.news;

import com.somnus.server.postservice.news.dto.NewsPostDTO;
import com.somnus.server.postservice.user.User;
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
    public List<NewsPostDTO> getDeletedNewsPosts(@RequestBody User user) {
        return newsService.getDeletedNewsPosts(user);
    }

    @GetMapping(value = "/restore-news-post/{id}")
    public void restoreNewsPost(@RequestBody User user, @PathVariable Integer id) {
        newsService.restoreNewsPost(user, id);
    }

    @GetMapping(value = "/news-post/{id}")
    public NewsPostDTO getNewsPostById(@PathVariable Integer id) {
        return newsService.getPostWithID(id);
    }

    @GetMapping(value = "/deleted-news-post/{id}")
    public NewsPostDTO getDeletedNewsPost(@RequestBody User user, @PathVariable Integer id) {
        return newsService.getDeletedNewsPost(user, id);
    }

    @DeleteMapping(value = "/news-post/delete/{id}")
    public void deleteNewsPost(@RequestBody User user, @PathVariable Integer id) {
        newsService.deleteNewsPost(user, id);
    }

    @PostMapping(value = "/news-post/create")
    public void createOrUpdateNewsPost(@RequestBody User user, @RequestBody NewsPostDTO newsPostDTO) {
        newsService.createOrUpdateNewsPost(user, newsPostDTO);
    }

}
