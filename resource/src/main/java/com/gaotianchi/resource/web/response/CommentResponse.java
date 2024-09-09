package com.gaotianchi.resource.web.response;

import com.gaotianchi.resource.persistence.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CommentResponse {
    private Long id;
    private String body;
    private Long parentId;
    private String commentStatus;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime lastUpdatedDatetime;
    private UserResponse author;
    private List<CommentResponse> replies;

    public CommentResponse(CommentEntity commentEntity) {
        setupData(commentEntity);
    }

    public CommentResponse(CommentEntity commentEntity, boolean withReplies) {
        if (withReplies) {
            if (!commentEntity.getReplies().isEmpty()) {
                List<CommentResponse> commentResponses = new ArrayList<>();
                for (CommentEntity commentEntity1 : commentEntity.getReplies()) {
                    commentResponses.add(new CommentResponse(commentEntity1, true));
                }
                this.replies = commentResponses;
            }
        }
        setupData(commentEntity);
    }

    private void setupData(CommentEntity commentEntity) {
        this.id = commentEntity.getId();
        this.body = commentEntity.getBody();
        if (commentEntity.getParentComment() != null) this.parentId = commentEntity.getParentComment().getId();
        this.commentStatus = commentEntity.getCommentStatus().name();
        this.creationDatetime = commentEntity.getCreationDatetime();
        this.lastUpdatedDatetime = commentEntity.getLastUpdatedDatetime();
    }
}
