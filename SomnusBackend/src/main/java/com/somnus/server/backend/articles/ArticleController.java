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
    public List<ArticleDto> getArticles(@RequestBody ArticlesRequestDto articlesRequest) {
        return articleService.getArticles(articlesRequest.getArticleTopicStringToObject());
    }

    @GetMapping(value = "/article/{id}")
    public ArticleDto getArticles(@PathVariable String id) {
        // TODO
        return null;
    }

    @PostMapping(value = "article/create")
    public void createArticle() {
        // TODO
    }
}
