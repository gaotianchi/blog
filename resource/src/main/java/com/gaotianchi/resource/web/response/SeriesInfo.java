package com.gaotianchi.resource.web.response;

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

    private String userLocation;
    private String coverLocation;
    private String articleListLocation;

    public SeriesInfo(SeriesEntity seriesEntity) {
        id = seriesEntity.getId();
        title = seriesEntity.getTitle();
        profile = seriesEntity.getProfile();
        creationDatetime = seriesEntity.getCreationDatetime();

        userLocation = "";
        coverLocation = "";
        articleListLocation = "";
    }
}
