package com.somnus.server.backend.articles.dto;

import com.somnus.server.backend.articles.domain.ArticleTopic;

import java.time.LocalDateTime;

public class ArticlesRequestDto {

    public String articleTopic;

    public ArticlesRequestDto(){}

    public String getArticleTopic() {
        return articleTopic;
    }

    public ArticleTopic getArticleTopicStringToObject() {
        return ArticleTopic.valueOf(this.articleTopic);
    }
}
