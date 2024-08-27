package com.gaotianchi.authorizationservice.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private List<String> roles;
    private OffsetDateTime lockedUntil;
    private List<String> privileges;
}
