package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.error.ImageNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.ImageEntity;
import com.gaotianchi.resourceservice.service.ImageService;
import com.gaotianchi.resourceservice.web.response.ImageOtd;
import com.gaotianchi.resourceservice.web.response.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/images/new")
    public ResponseEntity<ImageResponse> newImage(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            ImageResponse imageResponse = imageService.newImage(file, userDetails.getUsername());
            return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/images/all")
    public ResponseEntity<List<ImageResponse>> listImages(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            List<ImageResponse> imageResponses = imageService.listImages(userDetails.getUsername());
            return new ResponseEntity<>(imageResponses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
