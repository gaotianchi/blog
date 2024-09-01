package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CommentWithRepliesOtd extends CommentOtd{
    private List<CommentWithRepliesOtd> commentWithRepliesOtds;
    private Long parentCommentId;

    public CommentWithRepliesOtd(CommentEntity commentEntity) {
        super(commentEntity);
        if (commentEntity.getParentComment() != null) this.parentCommentId = commentEntity.getParentComment().getId();
        if (commentEntity.getReplies() != null) {
            List<CommentWithRepliesOtd> commentWithRepliesOtds1 = new ArrayList<>();
            for (CommentEntity commentEntity1 : commentEntity.getReplies()) {
                commentWithRepliesOtds1.add(new CommentWithRepliesOtd(commentEntity1));
            }
            this.commentWithRepliesOtds = commentWithRepliesOtds1;
        }

    }
}
