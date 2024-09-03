package com.gaotianchi.resourceservice.web.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewTokenRequest {
    private String email;
    private String password;
}
