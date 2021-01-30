package com.somnus.server.backend.articles;

import com.somnus.server.backend.articles.dto.ArticleDto;
import com.somnus.server.backend.articles.dto.ArticlesRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/articlesApi")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(value = "/articleList")
    public List<ArticleDto> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PostMapping(value = "/topicArticles")
    public List<ArticleDto> getArticlesOfTopic(@RequestBody ArticlesRequestDto articlesRequest) {
        return articleService.getArticlesOfTopic(articlesRequest.articleTopic);
    }

    @GetMapping(value = "/article/{id}")
    public ArticleDto getArticle(@PathVariable String id) {
        return articleService.getArticle(id);
    }

    @PostMapping(value = "article/create")
    public void createArticle(ArticleDto articleDto) {
        articleService.createArticle(articleDto);
    }

    @GetMapping(value = "/article/delete/{id}")
    public void deleteArticle(String id) {
        articleService.deleteArticle(id);
    }
}
