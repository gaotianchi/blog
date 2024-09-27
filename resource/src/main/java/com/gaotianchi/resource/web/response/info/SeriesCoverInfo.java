package com.gaotianchi.resource.web.response.info;

import com.gaotianchi.resource.persistence.entity.SeriesCoverEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class SeriesCoverInfo {
    private Long id;
    private String filename;
    private String url;
    private OffsetDateTime creationDatetime;

    private String userInfoLocation;
    private String seriesInfoLocation;

    public SeriesCoverInfo(SeriesCoverEntity seriesCoverEntity) {
        id = seriesCoverEntity.getId();
        filename = seriesCoverEntity.getFilename();
        url = seriesCoverEntity.getUrl();
        creationDatetime = seriesCoverEntity.getCreationDatetime();

        userInfoLocation = "http://localhost:8090/users/info/" + seriesCoverEntity.getUser().getId();
        SeriesEntity seriesEntity = seriesCoverEntity.getSeries();
        if (seriesEntity != null) seriesInfoLocation = "http://localhost:8090/series/info/" + seriesEntity.getId();
    }
}
