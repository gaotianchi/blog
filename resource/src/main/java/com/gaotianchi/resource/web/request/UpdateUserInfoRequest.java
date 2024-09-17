package com.gaotianchi.resource.web.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInfoRequest {
    private String penName;
    private String timezone;
    private Long avatarId;
    private String profile;
}
