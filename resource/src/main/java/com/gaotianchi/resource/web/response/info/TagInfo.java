package com.gaotianchi.resource.web.response.info;

import com.gaotianchi.resource.persistence.entity.TagEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class TagInfo {
    private Long id;
    private String name;
    private OffsetDateTime creationDatetime;
    private Integer articleCount;

    private String articleInfoPageLocation;

    public TagInfo(TagEntity tagEntity) {
        id = tagEntity.getId();
        name = tagEntity.getName();
        creationDatetime = tagEntity.getCreationDatetime();
        articleCount = tagEntity.getArticleCount();

        articleInfoPageLocation = "http://localhost:8090/articles/tag/" + id;
    }
}
