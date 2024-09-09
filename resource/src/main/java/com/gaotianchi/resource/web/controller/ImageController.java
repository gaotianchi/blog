package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.ImageResponse;
import com.gaotianchi.resource.web.service.imageservice.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public APIResponse<ImageResponse> newImage(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Jwt jwt) throws IOException {
        ImageResponse imageResponse = imageService.newImage(file, jwt.getSubject());
        return APIResponse.success(imageResponse);
    }

    @GetMapping("/images/all")
    public APIResponse<List<ImageResponse>> listImages(@AuthenticationPrincipal Jwt jwt) {
        List<ImageResponse> imageResponses = imageService.listImages(jwt.getSubject());
        return APIResponse.success(imageResponses);
    }

    @DeleteMapping("/images/delete/{imageId}")
    public APIResponse<Void> deleteImage(@AuthenticationPrincipal Jwt jwt, @PathVariable Long imageId) throws IOException {
        imageService.deleteImage(jwt.getSubject(), imageId);
        return APIResponse.success();
    }
}
