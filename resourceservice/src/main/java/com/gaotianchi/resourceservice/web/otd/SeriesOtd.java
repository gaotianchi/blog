package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.SeriesEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class SeriesOtd {
    private Long id;
    private String name;
    private OffsetDateTime creationDatetime;
    private ArticleImageOtd cover;
    public SeriesOtd(SeriesEntity seriesEntity) {
        this.id = seriesEntity.getId();
        this.name = seriesEntity.getName();
        this.creationDatetime = seriesEntity.getCreationDatetime();
        this.cover = new ArticleImageOtd(seriesEntity.getCover());
    }
}
