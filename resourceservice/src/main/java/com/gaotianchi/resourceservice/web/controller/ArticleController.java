package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.*;
import com.gaotianchi.resourceservice.persistence.entity.ImageEntity;
import com.gaotianchi.resourceservice.persistence.entity.SeriesEntity;
import com.gaotianchi.resourceservice.persistence.entity.TagEntity;
import com.gaotianchi.resourceservice.service.ArticleService;
import com.gaotianchi.resourceservice.web.request.UpdateArticleContentRequest;
import com.gaotianchi.resourceservice.web.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ArticleResponse> newArticle(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            ArticleResponse articleResponse = articleService.newArticle(userDetails.getUsername());
            return new ResponseEntity<>(articleResponse, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/articles/publish/{articleId}")
    public ResponseEntity<ArticleResponse> publishArticle(@PathVariable Long articleId) {
        try {
            ArticleResponse articleResponse = articleService.publishArticle(articleId);
            return new ResponseEntity<>(articleResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/articles/draft/{articleId}")
    public ResponseEntity<ArticleResponse> setToDraft(@PathVariable Long articleId) {
        try {
            ArticleResponse articleResponse = articleService.setToDraft(articleId);
            return new ResponseEntity<>(articleResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/articles/trash/{articleId}")
    public ResponseEntity<ArticleResponse> setToTrash(@PathVariable Long articleId) {
        try {
            ArticleResponse articleResponse = articleService.setToTrash(articleId);
            return new ResponseEntity<>(articleResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/articles/list")
    public ResponseEntity<List<ArticleResponse>> listArticles(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            List<ArticleResponse> articleResponses = articleService.listArticles(userDetails.getUsername());
            return new ResponseEntity<>(articleResponses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/articles/update-content/{articleId}")
    public ResponseEntity<ArticleResponse> updateContent(@PathVariable Long articleId, @RequestBody UpdateArticleContentRequest updateArticleContentRequest) {
        try {
            ArticleResponse articleResponse = articleService.updateContent(articleId, updateArticleContentRequest.getTitle(), updateArticleContentRequest.getBody(), updateArticleContentRequest.getSummary(), updateArticleContentRequest.getSlug());
            return new ResponseEntity<>(articleResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<ArticleCommentsOtd> getArticleComments(@PathVariable Long articleId) {
        try {
            ArticleCommentsOtd articleCommentsOtd = articleService.getArticleCommentsOtd(articleId);
            return new ResponseEntity<>(articleCommentsOtd, HttpStatus.OK);
        } catch (ArticleNotFoundException | CommentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/articles/{articleId}/series/{seriesId}")
    public ResponseEntity<SeriesOtd> updateArticleSeries(@PathVariable Long articleId, @PathVariable Long seriesId) {
        try {
            SeriesEntity seriesEntity = articleService.updateArticleSeries(articleId, seriesId);
            return new ResponseEntity<>(new SeriesOtd(seriesEntity), HttpStatus.OK);
        } catch (ArticleNotFoundException | SeriesNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/articles/{id}/series/remove")
    public ResponseEntity<Void> removeArticleSeries(@PathVariable Long id) {
        try {
            articleService.removeArticleSeries(id);
            return null;
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/articles/{articleId}/cover/{coverId}")
    public ResponseEntity<ArticleImageOtd> updateArticleCover(@PathVariable Long articleId, @PathVariable Long coverId) {
        try {
            ImageEntity imageEntity = articleService.updateArticleCover(articleId, coverId);
            return new ResponseEntity<>(new ArticleImageOtd(imageEntity), HttpStatus.OK);
        } catch (ArticleNotFoundException | ImageNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/articles/{articleId}/remove/tags/{tagId}")
    public ResponseEntity<Void> removeArticleTag(@PathVariable Long articleId, @PathVariable Long tagId) {
        try {
            articleService.removeArticleTag(tagId, articleId);
            return null;
        } catch (TagNotFoundException | ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/articles/{articleId}/add/tags/{tagId}")
    public ResponseEntity<TagOtd> addArticleTag(@PathVariable Long articleId, @PathVariable Long tagId) {
        try {
            TagEntity tagEntity = articleService.addArticleTag(articleId, tagId);
            return new ResponseEntity<>(new TagOtd(tagEntity), HttpStatus.OK);
        } catch (TagNotFoundException | ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/articles/{id}/tags/all")
    public ResponseEntity<List<TagOtd>> getArticleTags(@PathVariable Long id) {
        try {
            List<TagOtd> tagOtds = articleService.getArticleTags(id);
            return new ResponseEntity<>(tagOtds, HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
