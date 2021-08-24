package com.somnus.server.backend.news;

import com.somnus.server.backend.news.dto.NewsPostDTO;
import com.somnus.server.backend.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/news-api")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news-posts")
    public List<NewsPostDTO> getNewsPosts() {
        return newsService.getAllNews();
    }

    @GetMapping(value = "/news-post/{id}")
    public NewsPostDTO getNewsPostById(@PathVariable Integer id) {
        return newsService.getPostWithID(id);
    }

    @DeleteMapping(value = "/article/delete/{id}")
    public void deleteArticle(Principal principal, @PathVariable Integer id) {
        User user = (User) ((Authentication) principal).getPrincipal();

        newsService.deleteNewsPost(user, id);
    }

}
