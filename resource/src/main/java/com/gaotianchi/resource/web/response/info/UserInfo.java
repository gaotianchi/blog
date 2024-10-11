package com.gaotianchi.resource.web.response.info;

import com.gaotianchi.resource.persistence.entity.AvatarEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.TimeZone;

@Setter
@Getter
public class UserInfo {
    private Long id;
    private String penName;
    private String profile;
    private TimeZone timeZone;

    private String avatarUrl;

    private String avatarInfoLocation;
    private String articleInfoPageLocation;
    private String illustrationInfoPageLocation;
    private String seriesInfoPageLocation;
    private String seriesCoverInfoPageLocation;
    private String commentInfoPageLocation;


    public UserInfo(UserEntity userEntity) {
        id = userEntity.getId();
        penName = userEntity.getPenName();
        profile = userEntity.getProfile();
        timeZone = userEntity.getTimeZone();

        AvatarEntity avatarEntity = userEntity.getAvatar();
        if (avatarEntity != null) {
            avatarUrl = avatarEntity.getUrl();
            avatarInfoLocation = "http://localhost:8090/avatar/info/" + avatarEntity.getId();
        }
        articleInfoPageLocation = "http://localhost:8090/articles/user/" + id;
        commentInfoPageLocation = "";
        illustrationInfoPageLocation = "http://localhost:8090/illustrations/user/" + id;
        seriesCoverInfoPageLocation = "http://localhost:8090/series-cover/user/" + id;
        seriesInfoPageLocation = "http://localhost:8090/series/user/" + id;
    }
}
