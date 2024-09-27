package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.web.service.storage.avatar.AvatarStorageService;
import com.gaotianchi.resource.web.service.storage.cover.SeriesCoverStorageService;
import com.gaotianchi.resource.web.service.storage.illustration.IllustrationStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
public class ImageController {

    private final AvatarStorageService avatarStorageService;
    private final IllustrationStorageService illustrationStorageService;
    private final SeriesCoverStorageService seriesCoverStorageService;

    public ImageController(AvatarStorageService avatarStorageService, IllustrationStorageService illustrationStorageService, SeriesCoverStorageService seriesCoverStorageService) {
        this.avatarStorageService = avatarStorageService;
        this.illustrationStorageService = illustrationStorageService;
        this.seriesCoverStorageService = seriesCoverStorageService;
    }

    private MediaType getImageContentType(String filename) {
        String fileExtension = Utils.getFileExtension(filename);
        return switch (fileExtension) {
            case ".png" -> MediaType.IMAGE_PNG;
            case ".jpeg" -> MediaType.IMAGE_JPEG;
            case ".gif" -> MediaType.IMAGE_GIF;
            default -> MediaType.ALL;
        };
    }

    @GetMapping("/images/avatar/{filename}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String filename) throws MalformedURLException {
        Path avatarPath = avatarStorageService.getOriginalPath(filename);
        Resource resource = new UrlResource(avatarPath.toUri());
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(getImageContentType(filename))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }

    @GetMapping("/images/illustration/{filename}")
    public ResponseEntity<Resource> getIllustration(@PathVariable String filename, @RequestParam(value = "type", required = false) String type) throws MalformedURLException {
        Path illustrationPath;
        if (type != null && type.equalsIgnoreCase("original")) {
            illustrationPath = illustrationStorageService.getOriginalPath(filename);
        } else {
            illustrationPath = illustrationStorageService.getThumbnailPath(filename);
        }
        Resource resource = new UrlResource(illustrationPath.toUri());
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(getImageContentType(filename))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }

    @GetMapping("/images/series-cover/{filename}")
    public ResponseEntity<Resource> getSeriesCover(@PathVariable String filename, @RequestParam("type") String type) throws MalformedURLException {
        Path seriesCoverPath;
        if (type != null && type.equalsIgnoreCase("original")) {
            seriesCoverPath = seriesCoverStorageService.getOriginalPath(filename);
        } else {
            seriesCoverPath = seriesCoverStorageService.getThumbnailPath(filename);
        }
        Resource resource = new UrlResource(seriesCoverPath.toUri());
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(getImageContentType(filename))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}
