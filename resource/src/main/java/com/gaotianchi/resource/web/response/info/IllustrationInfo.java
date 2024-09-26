package com.gaotianchi.resource.web.response.info;

import com.gaotianchi.resource.persistence.entity.IllustrationEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class IllustrationInfo {
    private Long id;
    private String filename;
    private String title;
    private String alt;
    private String originalUrl;
    private String thumbnailUrl;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime updateDatetime;

    private String userLocation;
    private String articleListLocation;

    public IllustrationInfo(IllustrationEntity illustrationEntity) {
        id = illustrationEntity.getId();
        filename = illustrationEntity.getFilename();
        title = illustrationEntity.getTitle();
        alt = illustrationEntity.getAlt();
        originalUrl = illustrationEntity.getOriginalUrl();
        thumbnailUrl = illustrationEntity.getThumbnailUrl();
        creationDatetime = illustrationEntity.getCreationDatetime();
        updateDatetime = illustrationEntity.getUpdateDatetime();

        userLocation = "";
        articleListLocation = "";
    }
}
