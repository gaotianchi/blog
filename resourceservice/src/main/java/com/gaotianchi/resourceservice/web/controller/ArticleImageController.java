package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.entity.ArticleImageEntity;
import com.gaotianchi.resourceservice.service.ArticleImageService;
import com.gaotianchi.resourceservice.web.error.ArticleImageNotFoundException;
import com.gaotianchi.resourceservice.web.otd.UploadImageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ArticleImageController {

    private final ArticleImageService articleImageService;

    @Autowired
    public ArticleImageController(ArticleImageService articleImageService) {
        this.articleImageService = articleImageService;
    }

    @PostMapping("/images/upload")
    public ResponseEntity<UploadImageResult> uploadArticleImage(@RequestParam("file") MultipartFile file, @RequestParam("articleId") Long articleId) {
        try {
            ArticleImageEntity articleImageEntity = articleImageService.createArticleImage(file, articleId);
            return new ResponseEntity<>(new UploadImageResult(articleImageEntity), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/images/delete/{id}")
    public ResponseEntity<Void> deleteArticleImage(@PathVariable Long id) {
        try {
            articleImageService.deleteImage(id);
        } catch (ArticleImageNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
