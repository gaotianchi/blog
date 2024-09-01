package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.entity.TagEntity;
import com.gaotianchi.resourceservice.service.TagService;
import com.gaotianchi.resourceservice.web.dto.TagDto;
import com.gaotianchi.resourceservice.web.error.ArticleNotFoundException;
import com.gaotianchi.resourceservice.web.error.TagAlreadyExistException;
import com.gaotianchi.resourceservice.web.error.TagNotFoundException;
import com.gaotianchi.resourceservice.web.otd.TagOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        } catch (ArticleNotFoundException | TagAlreadyExistException e) {
            throw new RuntimeException(e);
        }
    }
}
