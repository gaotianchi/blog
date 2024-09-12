package com.gaotianchi.resource.web.service.imageservice;

import com.gaotianchi.resource.config.ImageConfig;
import com.gaotianchi.resource.persistence.entity.ImageEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.ImageRepo;
import com.gaotianchi.resource.web.error.EntityNotFoundException;
import com.gaotianchi.resource.web.response.ImageResponse;
import com.gaotianchi.resource.web.service.EntityBelongService;
import com.gaotianchi.resource.web.service.EntityFounderService;
import com.gaotianchi.resource.web.service.imagecompressionservice.ImageCompressionService;
import com.gaotianchi.resource.web.service.imagestorageservice.ImageStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImageService implements ImageServiceInterface {

    private final ImageRepo imageRepo;
    private final EntityFounderService entityFounderService;
    private final ImageStorageService imageStorageService;

    @Autowired
    public ImageService(ImageStorageService imageStorageService, ImageRepo imageRepo, ImageConfig imageConfig, EntityFounderService entityFounderService, EntityBelongService entityBelongService, ImageCompressionService imageCompressionService) {
        this.imageRepo = imageRepo;
        this.entityFounderService = entityFounderService;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public ImageResponse newImage(HttpServletRequest req, MultipartFile file, String username) throws EntityNotFoundException, IOException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        Map<String, String> imageStorageResponse = imageStorageService.save(req, file);
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUser(userEntity);
        imageEntity.setUrls(imageStorageResponse.toString());
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
