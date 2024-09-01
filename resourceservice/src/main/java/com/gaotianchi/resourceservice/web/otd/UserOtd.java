package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.TimeZone;

@Setter
@Getter
public class UserOtd {
    private Long id;
    private String penName;
    private TimeZone timeZone;
    private Integer score;
    private String avatarUrl;

    public UserOtd(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.penName = userEntity.getPenName();
        this.timeZone = userEntity.getTimeZone();
        this.score = userEntity.getScore();
        this.avatarUrl = null;
        if (userEntity.getAvatarEntity() != null) this.avatarUrl = userEntity.getAvatarEntity().getAvatarUrl();
    }
}
