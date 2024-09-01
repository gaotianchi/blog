package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.ArticleEntity;
import com.gaotianchi.resourceservice.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ArticleOtd {
    private Long id;
    private String title;
    private String body;
    private String summary;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime publishDatetime;
    private OffsetDateTime lastUpdatedDatetime;
    private ArticleStatus articleStatus;
    private String slug;
    private UserOtd userOtd;

    public ArticleOtd(ArticleEntity articleEntity) {
        this.id = articleEntity.getId();
        this.title = articleEntity.getTitle();
        this.body = articleEntity.getBody();
        this.summary = articleEntity.getSummary();
        this.creationDatetime = articleEntity.getCreationDatetime();
        this.publishDatetime = articleEntity.getPublishDatetime();
        this.lastUpdatedDatetime = articleEntity.getLastUpdatedDatetime();
        this.articleStatus = this.getArticleStatus();
        this.slug = this.getSlug();
        this.userOtd = new UserOtd(articleEntity.getAuthor());
    }
}
