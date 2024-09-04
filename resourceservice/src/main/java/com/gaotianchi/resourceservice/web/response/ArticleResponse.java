package com.gaotianchi.resourceservice.web.response;

import com.gaotianchi.resourceservice.persistence.entity.ArticleEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ArticleResponse {
    private Long id;
    private String title;
    private String body;
    private String summary;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime publishDatetime;
    private OffsetDateTime lastUpdatedDatetime;
    private String articleStatus;
    private String slug;

    public ArticleResponse(ArticleEntity articleEntity) {
        setupData(articleEntity);
    }

    private void setupData(ArticleEntity articleEntity) {
        this.id = articleEntity.getId();
        this.title = articleEntity.getTitle();
        this.body = articleEntity.getBody();
        this.summary = articleEntity.getSummary();
        this.creationDatetime = articleEntity.getCreationDatetime();
        this.publishDatetime = articleEntity.getPublishDatetime();
        this.lastUpdatedDatetime = articleEntity.getLastUpdatedDatetime();
        this.articleStatus = articleEntity.getArticleStatus().name();
        this.slug = articleEntity.getSlug();
    }
}
