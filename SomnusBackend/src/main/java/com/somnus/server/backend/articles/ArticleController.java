package com.somnus.server.backend.articles;

import com.somnus.server.backend.articles.dto.ArticleDto;
import com.somnus.server.backend.articles.dto.ArticlesRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void createArticle(@RequestBody ArticleDto articleDto) {
        articleService.createArticle(articleDto);
    }

    @GetMapping(value = "/article/delete/{id}")
    public void deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
    }
}
