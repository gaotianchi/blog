package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.entity.CommentEntity;
import com.gaotianchi.resourceservice.service.CommentService;
import com.gaotianchi.resourceservice.web.dto.CommentDto;
import com.gaotianchi.resourceservice.web.dto.UpdateCommentDto;
import com.gaotianchi.resourceservice.web.error.ArticleNotFoundException;
import com.gaotianchi.resourceservice.web.error.CommentNotFoundException;
import com.gaotianchi.resourceservice.web.error.UserNotFoundException;
import com.gaotianchi.resourceservice.web.otd.CommentOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommentOtd> newComment(@RequestBody CommentDto commentDto) {
        try {
            CommentEntity commentEntity = commentService.newComment(commentDto.getBody(), commentDto.getUserId(), commentDto.getArticleId(), Optional.ofNullable(commentDto.getParentCommentId()));
            return new ResponseEntity<>(new CommentOtd(commentEntity), HttpStatus.OK);
        } catch (UserNotFoundException | ArticleNotFoundException | CommentNotFoundException e) {
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
}
