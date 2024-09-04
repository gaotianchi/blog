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

    public SeriesResponse(SeriesEntity seriesEntity) {
        setupData(seriesEntity);
    }

    private void setupData(SeriesEntity seriesEntity) {
        this.id = seriesEntity.getId();
        this.name = seriesEntity.getName();
        this.creationDatetime = seriesEntity.getCreationDatetime();
    }
}
