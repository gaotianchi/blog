package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.*;
import com.gaotianchi.resourceservice.persistence.entity.ImageEntity;
import com.gaotianchi.resourceservice.persistence.entity.SeriesEntity;
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
    public ResponseEntity<ArticleResponse> updateContent(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @RequestBody UpdateArticleContentRequest updateArticleContentRequest) {
        try {
            ArticleResponse articleResponse = articleService.updateContent(userDetails.getUsername(), articleId, updateArticleContentRequest.getTitle(), updateArticleContentRequest.getBody(), updateArticleContentRequest.getSummary(), updateArticleContentRequest.getSlug());
            return new ResponseEntity<>(articleResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/articles/set-series/{articleId}/{seriesId}")
    public ResponseEntity<ArticleResponse> setSeries(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @PathVariable Long seriesId) {
        try {
            ArticleResponse articleResponse = articleService.setSeries(userDetails.getUsername(), articleId, seriesId);
            return new ResponseEntity<>(articleResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/articles/remove-series/{articleId}")
    public ResponseEntity<Void> removeSeries(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId) {
        try {
            articleService.removeSeries(userDetails.getUsername(), articleId);
            return null;
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/articles/set-cover/{articleId}/{coverId}")
    public ResponseEntity<ArticleResponse> setCover(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @PathVariable Long coverId) {
        try {
            ArticleResponse articleResponse = articleService.setCover(userDetails.getUsername(), articleId, coverId);
            return new ResponseEntity<>(articleResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/articles/remove-cover/{articleId}")
    public ResponseEntity<Void> removeCover(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId) {
        try {
            articleService.removeCover(userDetails.getUsername(), articleId);
            return null;
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/articles/add-tag/{articleId}/{tagId}")
    public ResponseEntity<TagResponse> addArticleTag(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @PathVariable Long tagId) {
        try {
            TagResponse tagResponse = articleService.addTag(userDetails.getUsername(), articleId, tagId);
            return new ResponseEntity<>(tagResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/articles/remove-tag/{articleId}/{tagId}")
    public ResponseEntity<Void> removeArticleTag(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long articleId, @PathVariable Long tagId) {
        try {
            articleService.removeTag(userDetails.getUsername(), tagId, articleId);
            return null;
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/articles/list-tags/{id}")
    public ResponseEntity<List<TagResponse>> getArticleTags(@PathVariable Long id) {
        try {
            List<TagResponse> tagResponses = articleService.listTags(id);
            return new ResponseEntity<>(tagResponses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/articles/list-comment-trees/{articleId}")
    public ResponseEntity<List<CommentResponse>> listCommentTrees(@PathVariable Long articleId) {
        try {
            List<CommentResponse> commentResponses = articleService.listArticleComments(articleId);
            return new ResponseEntity<>(commentResponses, HttpStatus.OK);
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

    @PatchMapping("/articles/{articleId}/cover/{coverId}")
    public ResponseEntity<ArticleImageOtd> updateArticleCover(@PathVariable Long articleId, @PathVariable Long coverId) {
        try {
            ImageEntity imageEntity = articleService.updateArticleCover(articleId, coverId);
            return new ResponseEntity<>(new ArticleImageOtd(imageEntity), HttpStatus.OK);
        } catch (ArticleNotFoundException | ImageNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
