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
    private String url;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime updateDatetime;
    private Integer articleCount = 0;

    private String userInfoLocation;
    private String articleInfoPageLocation;

    public IllustrationInfo(IllustrationEntity illustrationEntity) {
        id = illustrationEntity.getId();
        filename = illustrationEntity.getFilename();
        title = illustrationEntity.getTitle();
        alt = illustrationEntity.getAlt();
        url = illustrationEntity.getUrl();
        creationDatetime = illustrationEntity.getCreationDatetime();
        updateDatetime = illustrationEntity.getUpdateDatetime();
        articleCount = illustrationEntity.getArticleCount();

        userInfoLocation = "http://localhost:8090/users/info/" + illustrationEntity.getUser().getId();
        articleInfoPageLocation = "http://localhost:8090/articles/illustration/" + id;
    }
}
