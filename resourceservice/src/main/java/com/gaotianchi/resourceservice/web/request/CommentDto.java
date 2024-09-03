package com.gaotianchi.resourceservice.web.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {
    private Long userId;
    private String body;
    private Long parentCommentId;
    private Long articleId;
}
