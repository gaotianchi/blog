package com.gaotianchi.resource.web.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenResponse {
    private String accessToken;
    private String tokenType;
}
