package com.gaotianchi.resourceservice.web.response;

import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.TimeZone;

@Setter
@Getter
public class UserResponse {
    private Long id;
    private String email;
    private String penName;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime registrationDateTime;
    private String accountStatus;
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
        this.email = userEntity.getEmail();
        this.penName = userEntity.getPenName();
        this.lockedUntil = userEntity.getLockedUntil();
        this.registrationDateTime = userEntity.getRegistrationDateTime();
        this.accountStatus = userEntity.getAccountStatus().name();
        this.timeZone = userEntity.getTimeZone();
        this.score = userEntity.getScore();
    }
}
