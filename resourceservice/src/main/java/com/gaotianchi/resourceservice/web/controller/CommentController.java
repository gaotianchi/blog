package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.service.CommentService;
import com.gaotianchi.resourceservice.web.request.NewCommentRequest;
import com.gaotianchi.resourceservice.web.request.UpdateCommentRequest;
import com.gaotianchi.resourceservice.web.response.APIResponse;
import com.gaotianchi.resourceservice.web.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments/new")
    public APIResponse<CommentResponse> newComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody NewCommentRequest newCommentRequest) {
        CommentResponse commentResponse = commentService.newComment(userDetails.getUsername(), newCommentRequest.getBody(), newCommentRequest.getArticleId(), Optional.ofNullable(newCommentRequest.getParentId()));
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
        CommentResponse commentResponse = commentService.updateCommentContent(userDetails.getUsername(), id, updateCommentRequest.getBody());
        return APIResponse.success(commentResponse);
    }
}
