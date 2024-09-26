package com.gaotianchi.resource.web.response.info;

import com.gaotianchi.resource.persistence.entity.SeriesCoverEntity;
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

    private String userLocation;
    private String seriesLocation;

    public SeriesCoverInfo(SeriesCoverEntity seriesCoverEntity) {
        id = seriesCoverEntity.getId();
        filename = seriesCoverEntity.getFilename();
        url = seriesCoverEntity.getUrl();
        creationDatetime = seriesCoverEntity.getCreationDatetime();

        userLocation = "";
        seriesLocation = "";
    }
}
