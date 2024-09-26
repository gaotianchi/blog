package com.gaotianchi.resource.web.response.info;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class ArticleInfo {
    private Long id;
    private String title;
    private String summary;
    private String status;
    private String slug;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime publishDatetime;
    private OffsetDateTime lastUpdatedDatetime;

    private String bodyLocation;
    private String userLocation;
    private String seriesLocation;
    private String illustrationListLocation;
    private String commentListLocation;
    private String tagListLocation;
    private String voteRecordListLocation;

    public ArticleInfo(ArticleEntity articleEntity) {
        id = articleEntity.getId();
        title = articleEntity.getTitle();
        summary = articleEntity.getSummary();
        status = articleEntity.getStatus().name();
        slug = articleEntity.getSlug();
        creationDatetime = articleEntity.getCreationDatetime();
        publishDatetime = articleEntity.getPublishDatetime();
        lastUpdatedDatetime = articleEntity.getUpdateDatetime();

        bodyLocation = "";
        userLocation = "http://localhost:8090/users/info/" + articleEntity.getUser().getId();
        SeriesEntity seriesEntity = articleEntity.getSeries();
        if (seriesEntity != null) seriesLocation = "http://localhost:8090/series/info/" + seriesEntity.getId();
        illustrationListLocation = "";
        commentListLocation = "";
        tagListLocation = "";
        voteRecordListLocation = "";
    }
}
