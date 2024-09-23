package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.ImageResponse;
import com.gaotianchi.resource.web.service.imageservice.ImageService;
import jakarta.servlet.http.HttpServletRequest;
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
    public APIResponse<ImageResponse> newImage(HttpServletRequest req, @RequestParam("file") MultipartFile file, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "alt", required = false) String alt, @AuthenticationPrincipal Jwt jwt) throws IOException {
        ImageResponse imageResponse = imageService.newImage(req, file, title, alt, jwt.getSubject());
        return APIResponse.success(imageResponse);
    }

    @GetMapping("/images/all")
    public APIResponse<List<ImageResponse>> listUserImages(@AuthenticationPrincipal Jwt jwt, @RequestParam String field, @RequestParam Integer page) {
        List<ImageResponse> imageResponses = imageService.listUserImages(jwt.getSubject(), page, field);
        return APIResponse.success(imageResponses);
    }

    @PatchMapping("/images/link-to-article/{imageId}/{articleId}")
    public APIResponse<Void> linkToArticle(@AuthenticationPrincipal Jwt jwt, @PathVariable Long imageId, @PathVariable Long articleId) {
        imageService.linkToArticle(jwt.getSubject(), articleId, imageId);
        return APIResponse.success();
    }

    @PatchMapping("/images/link-to-article/{imageId}/{articleId}")
    public APIResponse<Void> unLinkToArticle(@AuthenticationPrincipal Jwt jwt, @PathVariable Long imageId, @PathVariable Long articleId) {
        imageService.unLinkToArticle(jwt.getSubject(), articleId, imageId);
        return APIResponse.success();
    }

    @DeleteMapping("/images/delete/{imageId}")
    public APIResponse<Void> deleteImage(@AuthenticationPrincipal Jwt jwt, @PathVariable Long imageId) throws IOException {
        imageService.deleteImage(jwt.getSubject(), imageId);
        return APIResponse.success();
    }
}
