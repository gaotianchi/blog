package com.gaotianchi.auth.web.response;

import com.gaotianchi.auth.persistence.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class UserResponse implements ResponseInterface<UserEntity> {
    private Long id;
    private String username;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime registrationDateTime;
    private String accountStatus;

    public UserResponse(UserEntity userEntity) {
        initOriginalData(userEntity);
    }

    @Override
    public void initOriginalData(UserEntity entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.lockedUntil = entity.getLockedUntil();
        this.registrationDateTime = entity.getRegistrationDateTime();
        this.accountStatus = entity.getAccountStatus().name();
    }
}
