package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.request.UpdateArticleContentRequest;
import com.gaotianchi.resource.web.request.UpdateArticleSummaryRequest;
import com.gaotianchi.resource.web.request.UpdateShallowDataRequest;
import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.ArticleInfo;
import com.gaotianchi.resource.web.response.info.IllustrationInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;
import com.gaotianchi.resource.web.response.info.TagInfo;
import com.gaotianchi.resource.web.service.article.ArticleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles/new")
    public APIResponse<ArticleInfo> newArticle(@AuthenticationPrincipal Jwt jwt) {
        ArticleInfo articleInfo = articleService.newArticle(jwt.getSubject());
        return APIResponse.success(articleInfo);
    }

    @DeleteMapping("/articles/delete/{id}")
    public APIResponse<Void> deleteArticle(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        articleService.deleteArticle(jwt.getSubject(), id);
        return APIResponse.success();
    }

    @PatchMapping("/articles/status/{id}/{statusName}")
    public APIResponse<Void> updateStatus(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @PathVariable String statusName) throws Exception {
        articleService.updateStatus(jwt.getSubject(), id, statusName);
        return APIResponse.success();
    }

    @PatchMapping("/articles/content/{id}")
    public APIResponse<Void> updateContent(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody UpdateArticleContentRequest updateArticleContentRequest) {
        articleService.updateContent(jwt.getSubject(), id, updateArticleContentRequest.getTitle(), updateArticleContentRequest.getBody());
        return APIResponse.success();
    }

    @PatchMapping("/articles/summary/{id}")
    public APIResponse<Void> updateSummary(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody UpdateArticleSummaryRequest updateArticleSummaryRequest) {
        articleService.updateSummary(jwt.getSubject(), id, updateArticleSummaryRequest.getSummary());
        return APIResponse.success();
    }

    @PatchMapping("/articles/slug/{id}")
    public APIResponse<Void> updateSlug(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody UpdateShallowDataRequest updateShallowDataRequest) {
        articleService.updateSlug(jwt.getSubject(), id, updateShallowDataRequest.getSlug());
        return APIResponse.success();
    }

    @PostMapping("/articles/series/{id}/{newSeriesId}")
    public APIResponse<SeriesInfo> setSeries(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @PathVariable Long newSeriesId) {
        SeriesInfo seriesInfo = articleService.setSeries(jwt.getSubject(), id, newSeriesId);
        return APIResponse.success(seriesInfo);
    }

    @DeleteMapping("/articles/series/{id}")
    public APIResponse<Void> removeSeries(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        articleService.removeSeries(jwt.getSubject(), id);
        return APIResponse.success();
    }

    @PostMapping("/articles/tag/{id}/{tagId}")
    public APIResponse<TagInfo> addTag(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @PathVariable Long tagId) {
        TagInfo tagInfo = articleService.addTag(jwt.getSubject(), id, tagId);
        return APIResponse.success(tagInfo);
    }

    @DeleteMapping("/articles/tag/{id}/{tagId}")
    public APIResponse<Void> removeTag(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @PathVariable Long tagId) {
        articleService.removeTag(jwt.getSubject(), id, tagId);
        return APIResponse.success();
    }

    @PostMapping("/articles/illustration/{id}/{newIllustrationId}")
    public APIResponse<IllustrationInfo> addIllustration(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @PathVariable Long newIllustrationId) {
        IllustrationInfo illustrationInfo = articleService.addIllustration(jwt.getSubject(), id, newIllustrationId);
        return APIResponse.success(illustrationInfo);
    }

    @DeleteMapping("/articles/illustration/{id}/{newIllustrationId}")
    public APIResponse<Void> removeIllustration(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @PathVariable Long newIllustrationId) {
        articleService.removeIllustration(jwt.getSubject(), id, newIllustrationId);
        return APIResponse.success();
    }

    @PostMapping("/articles/illustrations/{id}")
    public APIResponse<Void> resetIllustrationList(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestParam List<Long> illustrationIds) {
        articleService.resetIllustrationList(jwt.getSubject(), id, illustrationIds);
        return APIResponse.success();
    }

    @GetMapping("/articles/info/{id}")
    public APIResponse<ArticleInfo> getInfo(@PathVariable Long id) {
        ArticleInfo articleInfo = articleService.getInfo(id);
        return APIResponse.success(articleInfo);
    }

    @GetMapping("/articles/body/{id}")
    public APIResponse<String> getBody(@PathVariable Long id) {
        String body = articleService.getBody(id);
        return APIResponse.success(body);
    }

    @GetMapping("/articles/user/{userId}")
    public APIResponse<PageInfo<ArticleInfo>> getUserArticleInfoPage(@PathVariable Long userId, @RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "status", required = false) String status) {
        PageInfo<ArticleInfo> articleInfoPageInfo = articleService.getUserArticleInfoPage(userId, status, page);
        return APIResponse.success(articleInfoPageInfo);
    }

    @GetMapping("/articles/series/{seriesId}")
    public APIResponse<PageInfo<ArticleInfo>> getSeriesArticleInfoPage(@PathVariable Long seriesId, @RequestParam(value = "page", defaultValue = "0", required = false) int page) {
        PageInfo<ArticleInfo> articleInfoPageInfo = articleService.getSeriesArticleInfoPage(seriesId, page);
        return APIResponse.success(articleInfoPageInfo);
    }

    @GetMapping("/articles/tag/{tagId}")
    public APIResponse<PageInfo<ArticleInfo>> getTagArticleInfoPage(@PathVariable Long tagId, @RequestParam(value = "page", defaultValue = "0", required = false) int page) {
        PageInfo<ArticleInfo> articleInfoPageInfo = articleService.getTagArticleInfoPage(tagId, page);
        return APIResponse.success(articleInfoPageInfo);
    }

    @GetMapping("/articles/illustration/{illustrationId}")
    public APIResponse<PageInfo<ArticleInfo>> getIllustrationArticleInfoPage(@PathVariable Long illustrationId, @RequestParam(value = "page", defaultValue = "0", required = false) int page) {
        PageInfo<ArticleInfo> articleInfoPageInfo = articleService.getIllustrationArticleInfoPage(illustrationId, page);
        return APIResponse.success(articleInfoPageInfo);
    }

    @GetMapping("/articles/comment/{commentId}")
    public APIResponse<PageInfo<ArticleInfo>> getCommentArticleInfoPage(@PathVariable Long commentId, @RequestParam(value = "page", defaultValue = "0", required = false) int page) {
        PageInfo<ArticleInfo> articleInfoPageInfo = articleService.getIllustrationArticleInfoPage(commentId, page);
        return APIResponse.success(articleInfoPageInfo);
    }
}
