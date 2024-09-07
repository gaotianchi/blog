package com.gaotianchi.resourceservice.web.response;

import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.TimeZone;

@Setter
@Getter
public class UserResponse {
    private Long id;
    private String email;
    private String penName;
    private TimeZone timeZone;
    private Integer score = 0;
    private ImageResponse avatar;

    public UserResponse(UserEntity userEntity) {
        setupData(userEntity);
    }

    public UserResponse(UserEntity userEntity, boolean avatar) {
        setupData(userEntity);
        if (avatar) {
            this.avatar = new ImageResponse(userEntity.getAvatar());
        }
    }

    public void setupData(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getUsername();
        this.penName = userEntity.getPenName();
        this.timeZone = userEntity.getTimeZone();
        this.score = userEntity.getScore();
    }
}
