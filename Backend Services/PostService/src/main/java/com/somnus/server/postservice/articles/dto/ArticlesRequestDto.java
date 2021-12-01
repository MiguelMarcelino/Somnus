package com.somnus.server.postservice.articles.dto;

import com.somnus.server.postservice.topic.ArticleTopic;

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
