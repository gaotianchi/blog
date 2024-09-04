package com.gaotianchi.resourceservice.web.response;

import com.gaotianchi.resourceservice.persistence.entity.SeriesEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class SeriesResponse {
    private Long id;
    private String name;
    private OffsetDateTime creationDatetime;
    private ImageResponse cover;

    public SeriesResponse(SeriesEntity seriesEntity) {
        setupData(seriesEntity);
    }

    public SeriesResponse(SeriesEntity seriesEntity, boolean withCover) {
        setupData(seriesEntity);
        if (withCover) {
            this.cover = new ImageResponse(seriesEntity.getCover());
        }
    }

    private void setupData(SeriesEntity seriesEntity) {
        this.id = seriesEntity.getId();
        this.name = seriesEntity.getName();
        this.creationDatetime = seriesEntity.getCreationDatetime();
    }
}
