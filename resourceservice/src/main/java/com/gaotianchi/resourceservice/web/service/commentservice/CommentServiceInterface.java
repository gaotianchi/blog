package com.gaotianchi.resourceservice.web.service.commentservice;

import com.gaotianchi.resourceservice.web.response.CommentResponse;
import jakarta.annotation.Nullable;

public interface CommentServiceInterface {
    CommentResponse newComment(String email, String body, Long articleId, @Nullable Long parentCommentId);

    CommentResponse getCommentTree(Long id);

    void deleteComment(String email, Long id);

    CommentResponse updateContent(String email, Long id, String body);
}
