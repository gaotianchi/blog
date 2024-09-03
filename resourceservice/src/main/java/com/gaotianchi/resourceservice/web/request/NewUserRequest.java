package com.gaotianchi.resourceservice.web.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewUserRequest {
    private String email;
    private String penName;
    private String password;
}
