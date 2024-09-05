package com.gaotianchi.resourceservice.web.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenResponse {
    private String accessToken;
    private String tokenType;
}
