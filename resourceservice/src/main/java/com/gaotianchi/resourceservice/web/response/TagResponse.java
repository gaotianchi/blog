package com.gaotianchi.resourceservice.web.response;

import com.gaotianchi.resourceservice.persistence.entity.TagEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TagResponse {
    private Long id;
    private String name;
    private OffsetDateTime creationDatetime;
    private Integer numberOfArticles;

    public TagResponse(TagEntity tagEntity) {
        setupData(tagEntity);
    }

    private void setupData(TagEntity tagEntity) {
        this.id = tagEntity.getId();
        this.creationDatetime = tagEntity.getCreationDatetime();
        this.name = tagEntity.getName();
        this.numberOfArticles = tagEntity.getArticles().size();
    }
}
