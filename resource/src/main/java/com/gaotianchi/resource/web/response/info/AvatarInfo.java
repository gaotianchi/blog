package com.gaotianchi.resource.web.response.info;

import com.gaotianchi.resource.persistence.entity.AvatarEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class AvatarInfo {
    private Long id;
    private String filename;
    private String url;
    private boolean active;
    private OffsetDateTime creationDatetime;

    private String userLocation;

    public AvatarInfo(AvatarEntity avatarEntity) {
        id = avatarEntity.getId();
        filename = avatarEntity.getFilename();
        url = avatarEntity.getUrl();
        active = avatarEntity.isActive();
        creationDatetime = avatarEntity.getCreationDatetime();

        userLocation = "";
    }
}
