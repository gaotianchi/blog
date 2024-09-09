package com.gaotianchi.resource.web.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewUserRequest {
    private String email;
    private String penName;
}
