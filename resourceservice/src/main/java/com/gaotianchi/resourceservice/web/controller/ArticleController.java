package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.service.articleservice.ArticleService;
import com.gaotianchi.resourceservice.web.request.UpdateArticleContentRequest;
import com.gaotianchi.resourceservice.web.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles/new")
    public APIResponse<ArticleResponse> newArticle(@AuthenticationPrincipal UserDetails userDetails) {
        ArticleResponse articleResponse = articleService.newArticle(userDetails.getUsername());
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/publish/{articleId}")
    public APIResponse<ArticleResponse> publishArticle(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.publishArticle(userDetails.getUsername(), articleId);
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/draft/{articleId}")
    public APIResponse<ArticleResponse> setToDraft(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.setToDraft(userDetails.getUsername(), articleId);
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/trash/{articleId}")
    public APIResponse<ArticleResponse> setToTrash(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.setToTrash(userDetails.getUsername(), articleId);
        return APIResponse.success(articleResponse);
    }

    @GetMapping("/articles/list")
    public APIResponse<List<ArticleResponse>> listArticles(@AuthenticationPrincipal UserDetails userDetails) {
        List<ArticleResponse> articleResponses = articleService.listArticles(userDetails.getUsername());
        return APIResponse.success(articleResponses);
    }

    @PatchMapping("/articles/update-content/{articleId}")
    public APIResponse<ArticleResponse> updateContent(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @RequestBody UpdateArticleContentRequest updateArticleContentRequest) {
        ArticleResponse articleResponse = articleService.updateContent(userDetails.getUsername(), articleId, updateArticleContentRequest.getTitle(), updateArticleContentRequest.getBody(), updateArticleContentRequest.getSummary(), updateArticleContentRequest.getSlug());
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/set-series/{articleId}/{seriesId}")
    public APIResponse<ArticleResponse> setSeries(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @PathVariable Long seriesId) {
        ArticleResponse articleResponse = articleService.setSeries(userDetails.getUsername(), articleId, seriesId);
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/remove-series/{articleId}")
    public APIResponse<Void> removeSeries(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId) {
        articleService.removeSeries(userDetails.getUsername(), articleId);
        return APIResponse.success();
    }

    @PatchMapping("/articles/set-cover/{articleId}/{coverId}")
    public APIResponse<ArticleResponse> setCover(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @PathVariable Long coverId) {
        ArticleResponse articleResponse = articleService.setCover(userDetails.getUsername(), articleId, coverId);
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/remove-cover/{articleId}")
    public APIResponse<Void> removeCover(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId) {
        articleService.removeCover(userDetails.getUsername(), articleId);
        return APIResponse.success();
    }

    @PatchMapping("/articles/add-tag/{articleId}/{tagId}")
    public APIResponse<TagResponse> addArticleTag(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @PathVariable Long tagId) {
        TagResponse tagResponse = articleService.addTag(userDetails.getUsername(), articleId, tagId);
        return APIResponse.success(tagResponse);
    }

    @PatchMapping("/articles/remove-tag/{articleId}/{tagId}")
    public APIResponse<Void> removeArticleTag(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @PathVariable Long tagId) {
        articleService.removeTag(userDetails.getUsername(), tagId, articleId);
        return APIResponse.success();
    }

    @GetMapping("/articles/list-tags/{id}")
    public APIResponse<List<TagResponse>> getArticleTags(@PathVariable Long id) {
        List<TagResponse> tagResponses = articleService.listTags(id);
        return APIResponse.success(tagResponses);
    }

    @GetMapping("/articles/list-comment-trees/{articleId}")
    public APIResponse<List<CommentResponse>> listCommentTrees(@PathVariable Long articleId) {
        List<CommentResponse> commentResponses = articleService.listArticleComments(articleId);
        return APIResponse.success(commentResponses);
    }

    @PatchMapping("/articles/add-image/{articleId}/{imageId}")
    public APIResponse<ImageResponse> addImage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @PathVariable Long imageId) {
        ImageResponse imageResponse = articleService.addArticleImage(userDetails.getUsername(), articleId, imageId);
        return APIResponse.success(imageResponse);
    }

    @PatchMapping("/articles/remove-image/{articleId}/{imageId}")
    public APIResponse<ImageResponse> removeImage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @PathVariable Long imageId) {
        articleService.removeArticleImage(userDetails.getUsername(), articleId, imageId);
        return APIResponse.success();
    }

    @GetMapping("/articles/list-images/{articleId}")
    public APIResponse<List<ImageResponse>> addImage(@PathVariable Long articleId) {
        List<ImageResponse> imageResponses = articleService.listArticleImages(articleId);
        return APIResponse.success(imageResponses);
    }
}
