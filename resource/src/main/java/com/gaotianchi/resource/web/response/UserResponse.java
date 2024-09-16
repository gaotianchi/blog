package com.gaotianchi.resource.web.response;

import com.gaotianchi.resource.persistence.entity.ImageEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.TimeZone;

@Setter
@Getter
public class UserResponse {
    private Long id;
    private String username;
    private String penName;
    private TimeZone timezone;
    private Integer score = 0;
    private ImageResponse avatar;

    public UserResponse(UserEntity userEntity) {
        setupData(userEntity);
    }

    public UserResponse(UserEntity userEntity, boolean avatar) {
        setupData(userEntity);
        if (avatar) {
            ImageEntity imageEntity = userEntity.getAvatar();
            if (imageEntity == null) {
                this.avatar = null;
            } else {
                this.avatar = new ImageResponse(userEntity.getAvatar());
            }
        }
    }

    public void setupData(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.penName = userEntity.getPenName();
        this.timezone = userEntity.getTimeZone();
        this.score = userEntity.getScore();
    }
}
