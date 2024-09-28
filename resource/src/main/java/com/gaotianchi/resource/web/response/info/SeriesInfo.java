package com.gaotianchi.resource.web.response.info;

import com.gaotianchi.resource.persistence.entity.SeriesCoverEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class SeriesInfo {
    private Long id;
    private String title;
    private String profile;
    private OffsetDateTime creationDatetime;
    private int articleCount;

    private String userInfoLocation;
    private String coverInfoLocation;
    private String articleInfoPageLocation;

    public SeriesInfo(SeriesEntity seriesEntity) {
        id = seriesEntity.getId();
        title = seriesEntity.getTitle();
        profile = seriesEntity.getProfile();
        creationDatetime = seriesEntity.getCreationDatetime();
        articleCount = seriesEntity.getArticleCount();

        userInfoLocation = "http://localhost:8090/users/info/" + seriesEntity.getUser().getId();
        SeriesCoverEntity seriesCoverEntity = seriesEntity.getCover();
        if (seriesCoverEntity != null)
            coverInfoLocation = "http://localhost:8090/series-cover/info/" + seriesCoverEntity.getId();
        articleInfoPageLocation = "http://localhost:8090/articles/series/" + id;
    }
}
