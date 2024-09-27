package com.gaotianchi.resource.web.request;

import lombok.Getter;
import lombok.Setter;

import java.util.TimeZone;

@Getter
@Setter
public class UpdateUserInfoRequest {
    private String penName;
    private TimeZone timeZone;
    private String profile;
}
