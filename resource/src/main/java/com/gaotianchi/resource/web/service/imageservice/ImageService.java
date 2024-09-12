package com.gaotianchi.resource.web.service.imageservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.config.ImageConfig;
import com.gaotianchi.resource.persistence.entity.ImageEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.enums.CompressionLevel;
import com.gaotianchi.resource.persistence.repo.ImageRepo;
import com.gaotianchi.resource.web.error.EntityNotFoundException;
import com.gaotianchi.resource.web.response.ImageResponse;
import com.gaotianchi.resource.web.service.EntityFounderService;
import com.gaotianchi.resource.web.service.imagestorageservice.ImageStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImageService implements ImageServiceInterface {

    private final ImageRepo imageRepo;
    private final EntityFounderService entityFounderService;
    private final ImageStorageService imageStorageService;
    private final ImageConfig imageConfig;
    private final ObjectMapper objectMapper;

    @Autowired
    public ImageService(ImageStorageService imageStorageService, ImageRepo imageRepo, EntityFounderService entityFounderService, ImageConfig imageConfig, ObjectMapper objectMapper) {
        this.imageRepo = imageRepo;
        this.entityFounderService = entityFounderService;
        this.imageStorageService = imageStorageService;
        this.imageConfig = imageConfig;
        this.objectMapper = objectMapper;
    }

    @Override
    public ImageResponse newImage(HttpServletRequest req, MultipartFile file, String username) throws EntityNotFoundException, IOException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        Map<CompressionLevel, Path> imageStorageResponse = imageStorageService.save(req, file);
        Map<String, String> urlMap = new HashMap<>();
        for (CompressionLevel k : imageStorageResponse.keySet()) {
            String filename = imageStorageResponse.get(k).toString().replace(imageConfig.getStorage().getRootPath(), "");
            String url = Utils.getImageUrl(req, filename);
            urlMap.put(k.name(), url);
        }
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUser(userEntity);
        imageEntity.setUrls(objectMapper.writeValueAsString(urlMap));
        imageEntity = imageRepo.save(imageEntity);
        return new ImageResponse(imageEntity);
    }

    @Override
    public List<ImageResponse> listImages(String username) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        return userEntity.getImageEntities().stream()
                .map(ImageResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteImage(String username, Long imageId) throws EntityNotFoundException, IOException {

    }
}
