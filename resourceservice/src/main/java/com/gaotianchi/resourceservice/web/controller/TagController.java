package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.ArticleNotFoundException;
import com.gaotianchi.resourceservice.error.TagAlreadyExistException;
import com.gaotianchi.resourceservice.error.TagNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.TagEntity;
import com.gaotianchi.resourceservice.service.TagService;
import com.gaotianchi.resourceservice.web.request.TagDto;
import com.gaotianchi.resourceservice.web.response.ArticleOtd;
import com.gaotianchi.resourceservice.web.response.TagOtd;
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
    public ResponseEntity<TagOtd> newTag(@RequestBody TagDto tagDto) {
        try {
            TagEntity tagEntity = tagService.createNewTag(tagDto.getName(), tagDto.getArticleId());
            return new ResponseEntity<>(new TagOtd(tagEntity), HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (TagAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/tags/delete/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        try {
            tagService.deleteTag(id);
            return null;
        } catch (TagNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/tags/all")
    public ResponseEntity<List<TagOtd>> getAllTags() {
        try {
            List<TagOtd> tagOtds = tagService.getAllTags();
            return new ResponseEntity<>(tagOtds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/tags/{id}/articles/all")
    public ResponseEntity<List<ArticleOtd>> getAllArticlesFromTag(@PathVariable Long id) {
        try {
            List<ArticleOtd> articleOtds = tagService.getAllArticlesFromTags(id);
            return new ResponseEntity<>(articleOtds, HttpStatus.OK);
        } catch (TagNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
