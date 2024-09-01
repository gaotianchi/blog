package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class CommentOtd {
    private Long id;
    private String body;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime lastUpdatedDatetime;
    private UserOtd userOtd;
    private Integer like;
    private Integer dislike;

    public CommentOtd(CommentEntity commentEntity) {
        this.userOtd = new UserOtd(commentEntity.getAuthor());
        this.id = commentEntity.getId();
        this.body = commentEntity.getBody();
        this.creationDatetime = commentEntity.getCreationDatetime();
        this.lastUpdatedDatetime = commentEntity.getLastUpdatedDatetime();
    }
}
