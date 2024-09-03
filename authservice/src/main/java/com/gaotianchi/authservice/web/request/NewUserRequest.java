package com.gaotianchi.authservice.web.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class NewUserRequest {
    private String email;
    private String password;
    private List<String> roles;
}
