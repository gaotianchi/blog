package com.gaotianchi.resource.web.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateShallowDataRequest {
    private String title;
    private String body;
    private String summary;
    private String status;
    private String slug;
}
