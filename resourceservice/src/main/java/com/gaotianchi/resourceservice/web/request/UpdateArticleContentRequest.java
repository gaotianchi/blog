package com.gaotianchi.resourceservice.web.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateArticleContentRequest {
    private String title;
    private String body;
    private String summary;
    private String slug;
}
