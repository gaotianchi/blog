package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.request.UpdateShallowDataRequest;
import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.info.ArticleInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;
import com.gaotianchi.resource.web.response.info.TagInfo;
import com.gaotianchi.resource.web.service.article.ArticleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles/new/")
    public APIResponse<ArticleInfo> newArticle(@AuthenticationPrincipal Jwt jwt) {
        ArticleInfo articleInfo = articleService.newArticle(jwt.getSubject());
        return APIResponse.success(articleInfo);
    }

    @DeleteMapping("/articles/delete/{id}")
    public APIResponse<Void> deleteArticle(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        articleService.deleteArticle(jwt.getSubject(), id);
        return APIResponse.success();
    }

    @GetMapping("/articles/info/{id}")
    public APIResponse<ArticleInfo> getInfo(@PathVariable Long id) {
        ArticleInfo articleInfo = articleService.getInfo(id);
        return APIResponse.success(articleInfo);
    }

    @PatchMapping("/articles/status/{id}")
    public APIResponse<Void> updateStatus(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody UpdateShallowDataRequest updateShallowDataRequest) throws Exception {
        articleService.updateStatus(jwt.getSubject(), id, updateShallowDataRequest.getStatus());
        return APIResponse.success();
    }

    @PatchMapping("/articles/title/{id}")
    public APIResponse<Void> updateTitle(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody UpdateShallowDataRequest updateShallowDataRequest) {
        articleService.updateTitle(jwt.getSubject(), id, updateShallowDataRequest.getTitle());
        return APIResponse.success();
    }

    @PatchMapping("/articles/summary/{id}")
    public APIResponse<Void> updateSummary(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody UpdateShallowDataRequest updateShallowDataRequest) {
        articleService.updateSummary(jwt.getSubject(), id, updateShallowDataRequest.getSummary());
        return APIResponse.success();
    }

    @PatchMapping("/articles/slug/{id}")
    public APIResponse<Void> updateSlug(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody UpdateShallowDataRequest updateShallowDataRequest) {
        articleService.updateSlug(jwt.getSubject(), id, updateShallowDataRequest.getSlug());
        return APIResponse.success();
    }

    @PatchMapping("/articles/body/{id}")
    public APIResponse<Void> updateBody(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody UpdateShallowDataRequest updateShallowDataRequest) {
        articleService.updateBody(jwt.getSubject(), id, updateShallowDataRequest.getBody());
        return APIResponse.success();
    }

    @GetMapping("/articles/body/{id}")
    public APIResponse<String> getBody(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        String body = articleService.getBody(jwt.getSubject(), id);
        return APIResponse.success(body);
    }

    @PatchMapping("/articles/set-series/{id}/{seriesId}")
    public APIResponse<SeriesInfo> setSeries(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @PathVariable Long seriesId) {
        SeriesInfo seriesInfo = articleService.setSeries(jwt.getSubject(), id, seriesId);
        return APIResponse.success(seriesInfo);
    }

    @PatchMapping("/articles/remove-series/{id}")
    public APIResponse<Void> removeSeries(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        articleService.removeSeries(jwt.getSubject(), id);
        return APIResponse.success();
    }

    @PatchMapping("/articles/add-tag/{id}/{tagId}")
    public APIResponse<TagInfo> addTag(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @PathVariable Long tagId) {
        TagInfo tagInfo = articleService.addTag(jwt.getSubject(), id, tagId);
        return APIResponse.success(tagInfo);
    }

    @PatchMapping("/articles/remove-tag/{id}/{tagId}")
    public APIResponse<Void> removeTag(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @PathVariable Long tagId) {
        articleService.removeTag(jwt.getSubject(), id, tagId);
        return APIResponse.success();
    }
}
