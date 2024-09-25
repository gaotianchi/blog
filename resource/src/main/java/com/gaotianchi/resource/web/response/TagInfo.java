package com.gaotianchi.resource.web.response;

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

    private String articleListLocation;

    public TagInfo(TagEntity tagEntity) {
        id = tagEntity.getId();
        name = tagEntity.getName();
        creationDatetime = tagEntity.getCreationDatetime();
        articleCount = tagEntity.getArticleList().size();

        articleListLocation = "";
    }
}
