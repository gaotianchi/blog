package com.gaotianchi.resource.web.response.info;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
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
    private String authorLocation;
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
        authorLocation = "";
        seriesLocation = "";
        illustrationListLocation = "";
        commentListLocation = "";
        tagListLocation = "";
        voteRecordListLocation = "";
    }
}
