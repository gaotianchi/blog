package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.TagInfo;
import com.gaotianchi.resource.web.service.tag.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tags/new/{name}")
    public APIResponse<TagInfo> newTag(@PathVariable String name) {
        return APIResponse.success(tagService.newTag(name));
    }

    @DeleteMapping("/tags/delete/{id}")
    public APIResponse<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return APIResponse.success();
    }

    @GetMapping("/tags/info/{id}")
    public APIResponse<TagInfo> getInfo(@PathVariable Long id) {
        return APIResponse.success(tagService.getInfo(id));
    }

    @GetMapping("/tags")
    public APIResponse<PageInfo<TagInfo>> getPageInfo(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return APIResponse.success(tagService.getPageInfo(page));
    }

    @GetMapping("/tag-names")
    public APIResponse<List<String>> getAllNames() {
        return APIResponse.success(tagService.getAllNames());
    }

    @GetMapping("/tags/article/{articleId}")
    public APIResponse<List<TagInfo>> getArticleTagList(@PathVariable Long articleId) {
        return APIResponse.success(tagService.getArticleTagList(articleId));
    }
}
