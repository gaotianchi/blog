package com.gaotianchi.resource.web.response.info;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import com.gaotianchi.resource.persistence.entity.TagEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String penName;
    private String seriesName;
    private List<String> tagNames;
    private String coverUrl;

    private String bodyValueLocation;
    private String userInfoLocation;
    private String seriesInfoLocation;

    private String illustrationInfoPageLocation;
    private String commentInfoPageLocation;
    private String tagInfoPageLocation;

    public ArticleInfo(ArticleEntity articleEntity) {
        id = articleEntity.getId();
        title = articleEntity.getTitle();
        summary = articleEntity.getSummary();
        status = articleEntity.getStatus().name();
        slug = articleEntity.getSlug();
        creationDatetime = articleEntity.getCreationDatetime();
        publishDatetime = articleEntity.getPublishDatetime();
        lastUpdatedDatetime = articleEntity.getUpdateDatetime();

        penName = articleEntity.getUser().getPenName();
        tagNames = new ArrayList<>();
        for (TagEntity tagEntity : articleEntity.getTagList()) {
            tagNames.add(tagEntity.getName());
        }
        if (articleEntity.getIllustrationList().stream().findFirst().isPresent()) {
            coverUrl = articleEntity.getIllustrationList().stream().findFirst().get().getUrl();
        }

        bodyValueLocation = "http://localhost:8090/articles/body/" + id;
        userInfoLocation = "http://localhost:8090/users/info/" + articleEntity.getUser().getId();
        SeriesEntity seriesEntity = articleEntity.getSeries();
        if (seriesEntity != null) {
            seriesInfoLocation = "http://localhost:8090/series/info/" + seriesEntity.getId();
            seriesName = seriesEntity.getTitle();
        }
        illustrationInfoPageLocation = "http://localhost:8090/illustrations/article/" + id;
        tagInfoPageLocation = "http://localhost:8090/tags/article/" + id;
        commentInfoPageLocation = "";
    }
}
