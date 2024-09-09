package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.request.NewCommentRequest;
import com.gaotianchi.resource.web.request.UpdateCommentRequest;
import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.CommentResponse;
import com.gaotianchi.resource.web.service.commentservice.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments/new")
    public APIResponse<CommentResponse> newComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody NewCommentRequest newCommentRequest) {
        CommentResponse commentResponse = commentService.newComment(userDetails.getUsername(), newCommentRequest.getBody(), newCommentRequest.getArticleId(), newCommentRequest.getParentId());
        return APIResponse.success(commentResponse);
    }

    @GetMapping("/comments/list-replies-tree/{id}")
    public APIResponse<CommentResponse> listRepliesTree(@PathVariable Long id) {
        CommentResponse commentResponse = commentService.getCommentTree(id);
        return APIResponse.success(commentResponse);
    }

    @DeleteMapping("/comments/delete/{id}")
    public APIResponse<Void> deleteComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        commentService.deleteComment(userDetails.getUsername(), id);
        return APIResponse.success();
    }

    @PatchMapping("/comments/update/{id}")
    public APIResponse<CommentResponse> updateComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody UpdateCommentRequest updateCommentRequest) {
        CommentResponse commentResponse = commentService.updateContent(userDetails.getUsername(), id, updateCommentRequest.getBody());
        return APIResponse.success(commentResponse);
    }
}
