package com.gaotianchi.resourceservice.web.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleCommentsOtd {
    private List<CommentWithRepliesOtd> commentWithRepliesOtds;
}
