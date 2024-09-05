package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.config.StorageProperties;
import com.gaotianchi.resourceservice.persistence.entity.ImageEntity;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import com.gaotianchi.resourceservice.persistence.enums.ArticleImageType;
import com.gaotianchi.resourceservice.persistence.repo.ImageRepo;
import com.gaotianchi.resourceservice.web.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.web.response.ImageResponse;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final StorageService storageService;
    private final ImageRepo imageRepo;
    private final StorageProperties storageProperties;
    private final CompressionService compressionService;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;

    @Autowired
    public ImageService(StorageService storageService, ImageRepo imageRepo, StorageProperties storageProperties, CompressionService compressionService, EntityFounderService entityFounderService, EntityBelongService entityBelongService) {
        this.storageService = storageService;
        this.imageRepo = imageRepo;
        this.storageProperties = storageProperties;
        this.compressionService = compressionService;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
    }

    public static String generateUniqueFileName() {
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = sdf.format(new Date(timestamp));
        int randomNum = new Random().nextInt(10000);
        return formattedDate + "_" + randomNum;
    }

    public static String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.contains(".")) {
            int lastDotIndex = fileName.lastIndexOf('.');
            return fileName.substring(lastDotIndex);
        }
        throw new InvalidFileNameException(fileName, "检索不到文件格式！");
    }

    public ImageResponse newImage(MultipartFile file, String email) throws IOException, EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        ImageEntity imageEntity = saveImage(file);
        imageEntity.setUser(userEntity);
        imageEntity = imageRepo.save(imageEntity);
        return new ImageResponse(imageEntity);
    }

    public List<ImageResponse> listImages(String email) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        return userEntity.getImageEntities().stream()
                .map(ImageResponse::new)
                .collect(Collectors.toList());
    }

    public ImageEntity saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new FileNotFoundException();
        String fileDirName = generateUniqueFileName();
        String fileExtension = getFileExtension(file);
        Path thumbnailRelativePath = Paths.get(storageProperties.getArticleImageUri()).resolve(fileDirName).resolve(ArticleImageType.THUMBNAIL.name() + fileExtension);
        Path originalRelativePath = Paths.get(storageProperties.getArticleImageUri()).resolve(fileDirName).resolve(ArticleImageType.ORIGINAL.name() + fileExtension);
        String originalUrl = storeOriginalArticleImage(file, originalRelativePath);
        String thumbnailUrl = storeThumbnailArticleImage(originalRelativePath, thumbnailRelativePath);
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setFileDirName(fileDirName);
        imageEntity.setOriginalUrl(originalUrl);
        imageEntity.setFileExtension(fileExtension);
        imageEntity.setThumbnailUrl(thumbnailUrl);
        return imageEntity;
    }

    public void deleteImage(String email, Long imageId) throws IOException, EntityNotFoundException {
        ImageEntity imageEntity = entityBelongService.imageBelongToUser(email, imageId);
        Path fileDirPath = findImageByDirName(imageEntity.getFileDirName());
        deleteImages(fileDirPath, imageEntity.getFileExtension());
        imageRepo.delete(imageEntity);
    }

    public Path findImageByDirName(String fileDirName) {
        return Paths.get(storageProperties.getRootLocation()).resolve(storageProperties.getArticleImageUri()).resolve(fileDirName);
    }

    public void deleteImages(Path fileDirPath, String fileExtension) throws IOException {
        Path thumbnail = fileDirPath.resolve(ArticleImageType.THUMBNAIL.name() + fileExtension);
        Path original = fileDirPath.resolve(ArticleImageType.ORIGINAL.name() + fileExtension);
        Files.delete(thumbnail);
        Files.delete(original);
        Files.delete(fileDirPath);
    }

    public String storeOriginalArticleImage(MultipartFile file, Path relativePath) {
        return storageService.store(file, relativePath);
    }

    public String storeThumbnailArticleImage(Path src, Path dst) throws IOException {
        return compressionService.compress(src, dst);
    }
}
