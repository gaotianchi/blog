package com.gaotianchi.resource.web.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCommentRequest {
    private String body;
    private Long parentId;
    private Long articleId;
}
