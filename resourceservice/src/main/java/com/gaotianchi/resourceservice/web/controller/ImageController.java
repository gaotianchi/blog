package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.entity.ImageEntity;
import com.gaotianchi.resourceservice.service.ImageService;
import com.gaotianchi.resourceservice.web.error.ImageNotFoundException;
import com.gaotianchi.resourceservice.web.otd.ArticleImageOtd;
import com.gaotianchi.resourceservice.web.otd.ImageOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/images/articles/{articleId}/upload")
    public ResponseEntity<ArticleImageOtd> uploadArticleImage(@RequestParam("file") MultipartFile file, @PathVariable Long articleId) {
        try {
            ImageEntity imageEntity = imageService.createArticleImage(file, articleId);
            return new ResponseEntity<>(new ArticleImageOtd(imageEntity), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/images/upload")
    public ResponseEntity<ImageOtd> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            ImageEntity imageEntity = imageService.createImage(file);
            return new ResponseEntity<>(new ImageOtd(imageEntity), HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/images/delete/{id}")
    public ResponseEntity<Void> deleteArticleImage(@PathVariable Long id) {
        try {
            imageService.deleteImage(id);
        } catch (ImageNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
