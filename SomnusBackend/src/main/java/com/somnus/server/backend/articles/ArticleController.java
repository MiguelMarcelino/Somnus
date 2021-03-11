package com.somnus.server.backend.articles;

import com.somnus.server.backend.articles.dto.ArticleDto;
import com.somnus.server.backend.articles.dto.ArticlesRequestDto;
import com.somnus.server.backend.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/articles-api")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/articles")
    public List<ArticleDto> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PostMapping(value = "/topic-articles")
    public List<ArticleDto> getArticlesOfTopic(@RequestBody ArticlesRequestDto articlesRequest) {
        return articleService.getArticlesOfTopic(articlesRequest.articleTopic);
    }

    @GetMapping(value = "/article/{id}")
    public ArticleDto getArticle(@PathVariable Integer id) {
        return articleService.getArticle(id);
    }

    @PostMapping(value = "/article/create")
    public void createOrUpdateArticle(Principal principal, @RequestBody ArticleDto articleDto) {
        User user = (User) ((Authentication) principal).getPrincipal();

        articleService.createOrUpdateArticle(user, articleDto);
    }

    @DeleteMapping(value = "/article/delete/{id}")
    public void deleteArticle(Principal principal, @PathVariable Integer id) {
        User user = (User) ((Authentication) principal).getPrincipal();

        articleService.deleteArticle(user, id);
    }

    @GetMapping(value = "/article/search/{articleName}")
    public List<ArticleDto> searchArticles(@PathVariable String articleName) {
        return articleService.searchArticles(articleName);
    }
}
