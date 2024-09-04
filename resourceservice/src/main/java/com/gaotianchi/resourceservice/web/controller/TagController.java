package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.EntityAlreadyExistException;
import com.gaotianchi.resourceservice.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.service.TagService;
import com.gaotianchi.resourceservice.web.request.NewTagRequest;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.TagResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tags/new")
    public ResponseEntity<TagResponse> newTag(@RequestBody NewTagRequest newTagRequest) {
        try {
            TagResponse tagResponse = tagService.newTag(newTagRequest.getName());
            return new ResponseEntity<>(tagResponse, HttpStatus.OK);
        } catch (EntityAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tags/list")
    public ResponseEntity<List<TagResponse>> listTags() {
        try {
            List<TagResponse> tagResponses = tagService.listTags();
            return new ResponseEntity<>(tagResponses, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/tags/delete/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        try {
            tagService.deleteTag(id);
            return null;
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tags/list-articles/{id}")
    public ResponseEntity<List<ArticleResponse>> listArticles(@PathVariable Long id) {
        try {
            List<ArticleResponse> articleResponses = tagService.listArticles(id);
            return new ResponseEntity<>(articleResponses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
