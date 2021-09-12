package com.somnus.server.backend.articles.dto;

import com.somnus.server.backend.topic.ArticleTopic;

import java.io.Serializable;

public class ArticlesRequestDto implements Serializable {

    public String articleTopic;

    public ArticlesRequestDto(){}

    public String getArticleTopic() {
        return articleTopic;
    }

    public ArticleTopic getArticleTopicStringToObject() {
        return ArticleTopic.valueOf(this.articleTopic);
    }
}
