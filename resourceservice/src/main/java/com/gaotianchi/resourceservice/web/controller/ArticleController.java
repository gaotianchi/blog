package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.entity.ArticleEntity;
import com.gaotianchi.resourceservice.service.ArticleService;
import com.gaotianchi.resourceservice.web.dto.DraftDto;
import com.gaotianchi.resourceservice.web.dto.UpdateContentDto;
import com.gaotianchi.resourceservice.web.error.ArticleNotFoundException;
import com.gaotianchi.resourceservice.web.error.UnExpectedStatusException;
import com.gaotianchi.resourceservice.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles/draft")
    public ResponseEntity<ArticleEntity> createNewDraft(@RequestBody DraftDto draftDto) {
        try {
            ArticleEntity articleEntity = articleService.createNewDraft(draftDto.getUserId());
            return new ResponseEntity<>(articleEntity, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/articles/trash/{articleId}")
    public ResponseEntity<ArticleEntity> throwInTrashCan(@PathVariable Long articleId) {
        try {
            ArticleEntity articleEntity = articleService.throwInTrashCan(articleId);
            return new ResponseEntity<>(articleEntity, HttpStatus.OK);
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
    public ResponseEntity<ArticleEntity> publishDraft(@PathVariable Long articleId) {
        try {
            ArticleEntity articleEntity = articleService.publishDraft(articleId);
            return new ResponseEntity<>(articleEntity, HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnExpectedStatusException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/articles/draft/{articleId}")
    public ResponseEntity<ArticleEntity> convertToDraft(@PathVariable Long articleId) {
        try {
            ArticleEntity articleEntity = articleService.convertToDraft(articleId);
            return new ResponseEntity<>(articleEntity, HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnExpectedStatusException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/articles/content/{articleId}")
    public ResponseEntity<ArticleEntity> updateArticleContent(@PathVariable Long articleId, @RequestBody UpdateContentDto updateContentDto) {
        try {
            ArticleEntity articleEntity = articleService.updateArticleContent(articleId, updateContentDto.getTitle(), updateContentDto.getBody(), updateContentDto.getSummary(), updateContentDto.getSlug());
            return new ResponseEntity<>(articleEntity, HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}