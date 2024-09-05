package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.service.imageservice.ImageService;
import com.gaotianchi.resourceservice.web.response.APIResponse;
import com.gaotianchi.resourceservice.web.response.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    public APIResponse<ImageResponse> newImage(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        ImageResponse imageResponse = imageService.newImage(file, userDetails.getUsername());
        return APIResponse.success(imageResponse);
    }

    @GetMapping("/images/all")
    public APIResponse<List<ImageResponse>> listImages(@AuthenticationPrincipal UserDetails userDetails) {
        List<ImageResponse> imageResponses = imageService.listImages(userDetails.getUsername());
        return APIResponse.success(imageResponses);
    }

    @DeleteMapping("/images/delete/{imageId}")
    public APIResponse<Void> deleteImage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long imageId) throws IOException {
        imageService.deleteImage(userDetails.getUsername(), imageId);
        return APIResponse.success();
    }
}
