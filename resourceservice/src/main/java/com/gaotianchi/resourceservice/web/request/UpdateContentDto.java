package com.gaotianchi.resourceservice.web.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateContentDto {
    private String title;
    private String body;
    private String Summary;
    private String slug;
}