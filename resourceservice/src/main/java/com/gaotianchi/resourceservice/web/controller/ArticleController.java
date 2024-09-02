package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.entity.ArticleEntity;
import com.gaotianchi.resourceservice.entity.ArticleImageEntity;
import com.gaotianchi.resourceservice.entity.SeriesEntity;
import com.gaotianchi.resourceservice.entity.TagEntity;
import com.gaotianchi.resourceservice.repo.ArticleRepo;
import com.gaotianchi.resourceservice.service.ArticleService;
import com.gaotianchi.resourceservice.web.dto.DraftDto;
import com.gaotianchi.resourceservice.web.dto.UpdateContentDto;
import com.gaotianchi.resourceservice.web.error.*;
import com.gaotianchi.resourceservice.web.otd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles/draft")
    public ResponseEntity<ArticleOtd> createNewDraft(@RequestBody DraftDto draftDto) {
        try {
            ArticleEntity articleEntity = articleService.createNewDraft(draftDto.getUserId());
            return new ResponseEntity<>(articleService.getArticleOtd(articleEntity), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/articles/trash/{articleId}")
    public ResponseEntity<ArticleOtd> throwInTrashCan(@PathVariable Long articleId) {
        try {
            ArticleEntity articleEntity = articleService.throwInTrashCan(articleId);
            return new ResponseEntity<>(articleService.getArticleOtd(articleEntity), HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/articles/delete/{articleId}")
    public ResponseEntity<Void> deleteTrash(@PathVariable Long articleId) {
        try {
            articleService.deleteTrash(articleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnExpectedStatusException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/articles/publish/{articleId}")
    public ResponseEntity<ArticleOtd> publishDraft(@PathVariable Long articleId) {
        try {
            ArticleEntity articleEntity = articleService.publishDraft(articleId);
            return new ResponseEntity<>(articleService.getArticleOtd(articleEntity), HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnExpectedStatusException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/articles/draft/{articleId}")
    public ResponseEntity<ArticleOtd> convertToDraft(@PathVariable Long articleId) {
        try {
            ArticleEntity articleEntity = articleService.convertToDraft(articleId);
            return new ResponseEntity<>(articleService.getArticleOtd(articleEntity), HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnExpectedStatusException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/articles/content/{articleId}")
    public ResponseEntity<ArticleOtd> updateArticleContent(@PathVariable Long articleId, @RequestBody UpdateContentDto updateContentDto) {
        try {
            ArticleEntity articleEntity = articleService.updateArticleContent(articleId, updateContentDto.getTitle(), updateContentDto.getBody(), updateContentDto.getSummary(), updateContentDto.getSlug());
            return new ResponseEntity<>(new ArticleOtd(articleEntity), HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
            ArticleImageEntity articleImageEntity = articleService.updateArticleCover(articleId, coverId);
            return new ResponseEntity<>(new ArticleImageOtd(articleImageEntity), HttpStatus.OK);
        } catch (ArticleNotFoundException | ArticleImageNotFoundException e) {
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
