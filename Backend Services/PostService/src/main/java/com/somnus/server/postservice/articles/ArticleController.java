package com.somnus.server.postservice.articles;

import com.somnus.server.postservice.articles.dto.ArticleDto;
import com.somnus.server.postservice.articles.dto.ArticlesRequestDto;
import com.somnus.server.postservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<ArticleDto> getDeletedArticles(@RequestBody User user) {
        return articleService.getDeletedArticles(user);
    }

    @GetMapping(value = "/restore-article/{id}")
    public void restoreArticle(@RequestBody User user, @PathVariable Integer id) {
        articleService.restoreArticle(user, id);
    }

    @GetMapping(value = "/article/{id}")
    public ArticleDto getNonDeletedArticle(@PathVariable Integer id) {
        return articleService.getNonDeletedArticle(id);
    }

    @GetMapping(value = "/deleted-article/{id}")
    public ArticleDto getDeletedArticle(@RequestBody User user, @PathVariable Integer id) {
        return articleService.getDeletedArticle(user, id);
    }

    @PostMapping(value = "/topic-articles")
    public List<ArticleDto> getArticlesOfTopic(@RequestBody ArticlesRequestDto articlesRequest) {
        return articleService.getArticlesOfTopic(articlesRequest.articleTopic);
    }

    @PostMapping(value = "/article/create")
    public void createOrUpdateArticle(@RequestBody User user, @RequestBody ArticleDto articleDto) {
        articleService.createOrUpdateArticle(user, articleDto);
    }

    @DeleteMapping(value = "/article/delete/{id}")
    public void deleteArticle(@RequestBody User user, @PathVariable Integer id) {
        articleService.deleteArticle(user, id);
    }

    @GetMapping(value = "/article/search/{articleName}")
    public List<ArticleDto> searchArticles(@PathVariable String articleName) {
        return articleService.searchArticles(articleName);
    }
}
