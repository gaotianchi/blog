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

    public UserInfo(UserEntity userEntity) {
        id = userEntity.getId();
        penName = userEntity.getPenName();
        profile = userEntity.getProfile();
        timeZone = userEntity.getTimeZone();

        avatarLocation = "";
    }
}
