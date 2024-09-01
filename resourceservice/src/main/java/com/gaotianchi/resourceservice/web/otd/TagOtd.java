package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.TagEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TagOtd {
    private Long id;
    private String name;
    private OffsetDateTime creationDatetime;
    private Integer numberOfArticles;
    public TagOtd(TagEntity tagEntity) {
        this.id = tagEntity.getId();
        this.creationDatetime = tagEntity.getCreationDatetime();
        this.name = tagEntity.getName();
        this.numberOfArticles = tagEntity.getArticles().size();
    }
}
