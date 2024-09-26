package com.gaotianchi.resource.web.response.info;

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

    private String avatarLocation;
    private String pageArticleLocation;
    private String pageIllustrationLocation;
    private String pageSeriesLocation;
    private String pageSeriesCoverLocation;
    private String pageCommentLocation;


    public UserInfo(UserEntity userEntity) {
        id = userEntity.getId();
        penName = userEntity.getPenName();
        profile = userEntity.getProfile();
        timeZone = userEntity.getTimeZone();

        avatarLocation = "http://localhost:8090/avatar/info/" + userEntity.getAvatar().getId();
        pageArticleLocation = "";
        pageCommentLocation = "";
        pageIllustrationLocation = "";
        pageSeriesCoverLocation = "";
        pageSeriesLocation = "";
    }
}
