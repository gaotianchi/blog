package com.gaotianchi.resource.web.service.Illustration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.config.ImageConfig;
import com.gaotianchi.resource.persistence.entity.IllustrationEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.enums.CompressionLevel;
import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.IllustrationRepo;
import com.gaotianchi.resource.web.error.EntityNotFoundException;
import com.gaotianchi.resource.web.response.ArticleInfo;
import com.gaotianchi.resource.web.response.IllustrationInfo;
import com.gaotianchi.resource.web.response.ImageResponse;
import com.gaotianchi.resource.web.response.PageIllustrationInfo;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import com.gaotianchi.resource.web.service.storage.ImageStorageService;
import com.gaotianchi.resource.web.service.storage.illustration.IllustrationStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IllustrationService implements IllustrationServiceInterface {

    private final IllustrationRepo illustrationRepo;
    private final EntityFounderService entityFounderService;
    private final ImageStorageService imageStorageService;
    private final ImageConfig imageConfig;
    private final ObjectMapper objectMapper;
    private final EntityBelongService entityBelongService;
    private final ArticleRepo articleRepo;
    private final IllustrationStorageService illustrationStorageService;

    @Autowired
    public IllustrationService(ImageStorageService imageStorageService, IllustrationRepo illustrationRepo, EntityFounderService entityFounderService, ImageConfig imageConfig, ObjectMapper objectMapper, EntityBelongService entityBelongService, ArticleRepo articleRepo, IllustrationStorageService illustrationStorageService) {
        this.illustrationRepo = illustrationRepo;
        this.entityFounderService = entityFounderService;
        this.imageStorageService = imageStorageService;
        this.imageConfig = imageConfig;
        this.objectMapper = objectMapper;
        this.entityBelongService = entityBelongService;
        this.articleRepo = articleRepo;
        this.illustrationStorageService = illustrationStorageService;
    }

    @Override
    public IllustrationInfo newIllustration(String username, MultipartFile file, String title, String alt) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        String filename = illustrationStorageService.save(file);
        IllustrationEntity illustrationEntity = new IllustrationEntity();
        illustrationEntity.setFilename(filename);
        illustrationEntity.setUser(userEntity);
        illustrationEntity.setTitle(title);
        if (alt != null && !alt.isEmpty()) {
            illustrationEntity.setAlt(alt);
        }
        return new IllustrationInfo(illustrationEntity);
    }

    @Override
    public void deleteIllustration(String username, Long id) {

    }

    @Override
    public void updateContent(String username, String title, String alt) {

    }

    @Override
    public IllustrationInfo getInfo(String username, Long id) {
        return null;
    }

    @Override
    public PageIllustrationInfo getPageInfo(String username, Integer page) {
        return null;
    }

    @Override
    public List<ArticleInfo> getArticleList(String username, Long id) {
        return List.of();
    }

    public ImageResponse newImage(HttpServletRequest req, MultipartFile file, String title, String alt, String username) throws EntityNotFoundException, IOException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        Map<CompressionLevel, Path> imageStorageResponse = imageStorageService.save(req, file);
        Map<String, String> urlMap = new HashMap<>();
        String name = "";
        for (CompressionLevel k : imageStorageResponse.keySet()) {
            String filename = imageStorageResponse.get(k).toString().replace(imageConfig.getStorage().getRootPath(), "");
            String url = Utils.getImageUrl(req, filename);
            String[] parts = url.split("/");
            name = parts[parts.length - 2];
            urlMap.put(k.name(), url);
        }
        IllustrationEntity illustrationEntity = new IllustrationEntity();
        illustrationEntity.setUser(userEntity);
        illustrationEntity.setFilename(name);
        illustrationEntity.setCreationDatetime(OffsetDateTime.now());
        illustrationEntity.setUpdateDatetime(OffsetDateTime.now());
        if (title != null && !title.isEmpty()) {
            illustrationEntity.setTitle(title);
        }
        if (alt != null && !alt.isEmpty()) {
            illustrationEntity.setAlt(alt);
        }
        illustrationEntity.setUrls(objectMapper.writeValueAsString(urlMap));
        illustrationEntity = illustrationRepo.save(illustrationEntity);
        return new ImageResponse(illustrationEntity);
    }

    public List<ImageResponse> listUserImages(String username, Integer page, String field) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        Pageable pageable = PageRequest.of(page, 10);
        List<IllustrationEntity> imageEntities = switch (field) {
            case "avatar" ->
                    illustrationRepo.findByForAvatarIsTrueAndUserOrderByCreationDatetimeDesc(userEntity, pageable);
            case "series" ->
                    illustrationRepo.findByForSeriesIsTrueAndUserOrderByCreationDatetimeDesc(userEntity, pageable);
            default -> illustrationRepo.findByForArticleIsTrueAndUserOrderByCreationDatetimeDesc(userEntity, pageable);
        };
        return imageEntities.stream()
                .map(ImageResponse::new)
                .collect(Collectors.toList());
    }

    public void deleteImage(String username, Long imageId) throws EntityNotFoundException, IOException {
        IllustrationEntity illustrationEntity = entityBelongService.imageBelongToUser(username, imageId);
        Path imageDir = Utils.getImageDir(imageConfig.getStorage().getRootPath(), illustrationEntity.getFilename());
        if (Files.exists(imageDir) && Files.isDirectory(imageDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(imageDir)) {
                for (Path file : stream) {
                    Files.delete(file);
                }
            }
            Files.delete(imageDir);
            illustrationRepo.delete(illustrationEntity);
        } else {
            throw new EntityNotFoundException("The image directory does not exist or is not a directory.");
        }
    }


}
