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
    public List<ArticleDto> getAllNonDeletedArticles() {
        return articleService.getAllNonDeletedArticles();
    }

    @GetMapping(value = "/deleted-articles")
    public List<ArticleDto> getDeletedArticles(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();

        return articleService.getDeletedArticles(user);
    }

    @GetMapping(value = "/restore-article/{id}")
    public void restoreArticle(Principal principal, @PathVariable Integer id) {
        User user = (User) ((Authentication) principal).getPrincipal();

        articleService.restoreArticle(user, id);
    }

    @PostMapping(value = "/topic-articles")
    public List<ArticleDto> getArticlesOfTopic(@RequestBody ArticlesRequestDto articlesRequest) {
        return articleService.getArticlesOfTopic(articlesRequest.articleTopic);
    }

    @GetMapping(value = "/article/{id}")
    public ArticleDto getNonDeletedArticle(@PathVariable Integer id) {
        return articleService.getNonDeletedArticle(id);
    }

    @GetMapping(value = "/deleted-article/{id}")
    public ArticleDto getDeletedArticle(Principal principal, @PathVariable Integer id) {
        User user = (User) ((Authentication) principal).getPrincipal();

        return articleService.getDeletedArticle(user, id);
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
