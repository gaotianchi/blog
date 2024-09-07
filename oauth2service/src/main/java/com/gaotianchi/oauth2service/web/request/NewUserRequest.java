package com.gaotianchi.oauth2service.web.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewUserRequest {
    private String username;
    private String password;
}
