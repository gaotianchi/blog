package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.CommentNotFoundException;
import com.gaotianchi.resourceservice.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.CommentEntity;
import com.gaotianchi.resourceservice.service.CommentService;
import com.gaotianchi.resourceservice.web.request.NewCommentRequest;
import com.gaotianchi.resourceservice.web.request.UpdateCommentDto;
import com.gaotianchi.resourceservice.web.response.CommentOtd;
import com.gaotianchi.resourceservice.web.response.CommentResponse;
import com.gaotianchi.resourceservice.web.response.CommentWithRepliesOtd;
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
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return null;
        } catch (CommentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/comments/update/{id}")
    public ResponseEntity<CommentOtd> updateComment(@PathVariable Long id, @RequestBody UpdateCommentDto updateCommentDto) {
        try {
            CommentEntity commentEntity = commentService.updateCommentContent(id, updateCommentDto.getBody());
            return new ResponseEntity<>(commentService.getCommentOtd(commentEntity), HttpStatus.OK);
        } catch (CommentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentWithRepliesOtd> getComment(@PathVariable Long id) {
        try {
            CommentWithRepliesOtd commentWithRepliesOtd = commentService.getCommentWithReplies(id);
            return new ResponseEntity<>(commentWithRepliesOtd, HttpStatus.OK);
        } catch (CommentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
