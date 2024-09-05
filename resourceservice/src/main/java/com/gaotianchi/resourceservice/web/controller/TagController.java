package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.service.TagService;
import com.gaotianchi.resourceservice.web.request.NewTagRequest;
import com.gaotianchi.resourceservice.web.response.APIResponse;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.TagResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    public APIResponse<TagResponse> newTag(@RequestBody NewTagRequest newTagRequest) {
        TagResponse tagResponse = tagService.newTag(newTagRequest.getName());
        return APIResponse.success(tagResponse);
    }

    @GetMapping("/tags/list")
    public APIResponse<List<TagResponse>> listTags() {
        List<TagResponse> tagResponses = tagService.listTags();
        return APIResponse.success(tagResponses);
    }

    @DeleteMapping("/tags/delete/{id}")
    public APIResponse<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return APIResponse.success();
    }

    @GetMapping("/tags/list-articles/{id}")
    public APIResponse<List<ArticleResponse>> listArticles(@PathVariable Long id) {
        List<ArticleResponse> articleResponses = tagService.listArticles(id);
        return APIResponse.success(articleResponses);
    }
}
