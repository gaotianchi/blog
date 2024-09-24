package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.request.UpdateArticleContentRequest;
import com.gaotianchi.resource.web.response.*;
import com.gaotianchi.resource.web.service.articleservice.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public APIResponse<ArticleResponse> newArticle(@AuthenticationPrincipal Jwt jwt) {
        ArticleResponse articleResponse = articleService.newArticle(jwt.getSubject());
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/publish/{articleId}")
    public APIResponse<ArticleResponse> publishArticle(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.publishArticle(jwt.getSubject(), articleId);
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/draft/{articleId}")
    public APIResponse<ArticleResponse> setToDraft(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.setToDraft(jwt.getSubject(), articleId);
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/trash/{articleId}")
    public APIResponse<ArticleResponse> setToTrash(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.setToTrash(jwt.getSubject(), articleId);
        return APIResponse.success(articleResponse);
    }

    @GetMapping("/articles/list")
    public APIResponse<ArticlePageResponse> listArticles(@AuthenticationPrincipal Jwt jwt, @RequestParam Integer page) {
        ArticlePageResponse articleResponses = articleService.listArticles(jwt.getSubject(), page);
        return APIResponse.success(articleResponses);
    }

    @PatchMapping("/articles/update-content/{articleId}")
    public APIResponse<ArticleResponse> updateContent(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId, @RequestBody UpdateArticleContentRequest updateArticleContentRequest) {
        ArticleResponse articleResponse = articleService.updateContent(jwt.getSubject(), articleId, updateArticleContentRequest.getTitle(), updateArticleContentRequest.getBody(), updateArticleContentRequest.getSummary(), updateArticleContentRequest.getSlug());
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/set-series/{articleId}/{seriesId}")
    public APIResponse<ArticleResponse> setSeries(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId, @PathVariable Long seriesId) {
        ArticleResponse articleResponse = articleService.setSeries(jwt.getSubject(), articleId, seriesId);
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/remove-series/{articleId}")
    public APIResponse<Void> removeSeries(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId) {
        articleService.removeSeries(jwt.getSubject(), articleId);
        return APIResponse.success();
    }

    @PatchMapping("/articles/set-cover/{articleId}/{coverId}")
    public APIResponse<ArticleResponse> setCover(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId, @PathVariable Long coverId) {
        ArticleResponse articleResponse = articleService.setCover(jwt.getSubject(), articleId, coverId);
        return APIResponse.success(articleResponse);
    }

    @PatchMapping("/articles/remove-cover/{articleId}")
    public APIResponse<Void> removeCover(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId) {
        articleService.removeCover(jwt.getSubject(), articleId);
        return APIResponse.success();
    }

    @PatchMapping("/articles/add-tag/{articleId}/{tagId}")
    public APIResponse<TagResponse> addArticleTag(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId, @PathVariable Long tagId) {
        TagResponse tagResponse = articleService.addTag(jwt.getSubject(), articleId, tagId);
        return APIResponse.success(tagResponse);
    }

    @PatchMapping("/articles/remove-tag/{articleId}/{tagId}")
    public APIResponse<Void> removeArticleTag(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId, @PathVariable Long tagId) {
        articleService.removeTag(jwt.getSubject(), tagId, articleId);
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
    public APIResponse<ImageResponse> addImage(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId, @PathVariable Long imageId) {
        ImageResponse imageResponse = articleService.addArticleImage(jwt.getSubject(), articleId, imageId);
        return APIResponse.success(imageResponse);
    }

    @PatchMapping("/articles/remove-image/{articleId}/{imageId}")
    public APIResponse<ImageResponse> removeImage(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId, @PathVariable Long imageId) {
        articleService.removeArticleImage(jwt.getSubject(), articleId, imageId);
        return APIResponse.success();
    }

    @GetMapping("/articles/list-images/{articleId}")
    public APIResponse<List<ImageResponse>> addImage(@PathVariable Long articleId) {
        List<ImageResponse> imageResponses = articleService.listArticleImages(articleId);
        return APIResponse.success(imageResponses);
    }

    @GetMapping("/articles/content/{articleId}")
    public APIResponse<ArticleResponse> getArticleContent(@AuthenticationPrincipal Jwt jwt, @PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.getArticleMainContent(jwt.getSubject(), articleId);
        return APIResponse.success(articleResponse);
    }
}
