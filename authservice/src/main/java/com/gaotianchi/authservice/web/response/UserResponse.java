package com.gaotianchi.authservice.web.response;

import com.gaotianchi.authservice.entity.UserEntity;
import com.gaotianchi.authservice.enums.AccountStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class UserResponse {
    private Long id;
    private String email;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime registrationDateTime;
    private AccountStatus accountStatus;

    public UserResponse(UserEntity userEntity) {
        setupInfoData(userEntity);
    }

    public UserResponse(UserEntity userEntity, boolean details) {
        setupInfoData(userEntity);
    }

    public void setupInfoData(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.lockedUntil = userEntity.getLockedUntil();
        this.registrationDateTime = userEntity.getRegistrationDateTime();
        this.accountStatus = userEntity.getAccountStatus();
    }
}
