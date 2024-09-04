package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.service.CommentService;
import com.gaotianchi.resourceservice.web.request.NewCommentRequest;
import com.gaotianchi.resourceservice.web.request.UpdateCommentRequest;
import com.gaotianchi.resourceservice.web.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommentResponse> newComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody NewCommentRequest newCommentRequest) {
        try {
            CommentResponse commentResponse = commentService.newComment(userDetails.getUsername(), newCommentRequest.getBody(), newCommentRequest.getArticleId(), Optional.ofNullable(newCommentRequest.getParentId()));
            return new ResponseEntity<>(commentResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/comments/list-replies-tree/{id}")
    public ResponseEntity<CommentResponse> listRepliesTree(@PathVariable Long id) {
        try {
            CommentResponse commentResponse = commentService.getCommentTree(id);
            return new ResponseEntity<>(commentResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/comments/delete/{id}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        try {
            commentService.deleteComment(userDetails.getUsername(), id);
            return null;
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/comments/update/{id}")
    public ResponseEntity<CommentResponse> updateComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody UpdateCommentRequest updateCommentRequest) {
        try {
            CommentResponse commentResponse = commentService.updateCommentContent(userDetails.getUsername(), id, updateCommentRequest.getBody());
            return new ResponseEntity<>(commentResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
