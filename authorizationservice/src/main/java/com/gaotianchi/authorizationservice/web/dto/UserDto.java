package com.gaotianchi.authorizationservice.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String role;
    private OffsetDateTime lockedUntil;
}
