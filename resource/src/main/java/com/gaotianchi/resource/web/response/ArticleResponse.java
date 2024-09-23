package com.gaotianchi.resource.web.response;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.ImageEntity;
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
    private SeriesResponse series;
    private ImageResponse cover;
    private UserResponse author;

    public ArticleResponse(ArticleEntity articleEntity) {
        setupData(articleEntity);
    }

    public ArticleResponse(ArticleEntity articleEntity, boolean withSeries) {
        setupData(articleEntity);
        if (withSeries) {
            this.series = new SeriesResponse(articleEntity.getSeriesEntity(), true);
        }
    }

    public ArticleResponse(ArticleEntity articleEntity, boolean withSeries, boolean withCover) {
        setupData(articleEntity);
        if (withSeries) {
            this.series = new SeriesResponse(articleEntity.getSeriesEntity(), true);
        }
        if (withCover) {
            ImageEntity cover = articleEntity.getCover();
            if (cover == null) {
                cover = (ImageEntity) articleEntity.getArticleImages().toArray()[0];
            }
            if (cover != null) {
                this.cover = new ImageResponse(cover);
            }

        }
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
        this.author = new UserResponse(articleEntity.getAuthor());
    }
}
