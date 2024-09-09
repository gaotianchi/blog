package com.gaotianchi.auth.web.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewUserRequest {
    private String username;
    private String password;
}
