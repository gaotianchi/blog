package com.gaotianchi.resourceservice.web.response;

import com.gaotianchi.resourceservice.persistence.entity.SeriesEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class SeriesOtd {
    private Long id;
    private String name;
    private OffsetDateTime creationDatetime;
    private ImageOtd cover;
    private Integer numberOfArticles;
    public SeriesOtd(SeriesEntity seriesEntity) {
        this.id = seriesEntity.getId();
        this.name = seriesEntity.getName();
        this.creationDatetime = seriesEntity.getCreationDatetime();
        this.cover = new ImageOtd(seriesEntity.getCover());
        this.numberOfArticles = seriesEntity.getArticleEntities().size();
    }
}
