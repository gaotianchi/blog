package com.gaotianchi.resource.web.service.imageservice;

import com.gaotianchi.resource.config.StorageProperties;
import com.gaotianchi.resource.persistence.entity.ImageEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.enums.ArticleImageType;
import com.gaotianchi.resource.persistence.repo.ImageRepo;
import com.gaotianchi.resource.web.error.EntityNotFoundException;
import com.gaotianchi.resource.web.response.ImageResponse;
import com.gaotianchi.resource.web.service.CompressionService;
import com.gaotianchi.resource.web.service.EntityBelongService;
import com.gaotianchi.resource.web.service.EntityFounderService;
import com.gaotianchi.resource.web.service.StorageService;
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
public class ImageService implements ImageServiceInterface {

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

    @Override
    public ImageResponse newImage(MultipartFile file, String email) throws EntityNotFoundException, IOException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        ImageEntity imageEntity = saveImage(file);
        imageEntity.setUser(userEntity);
        imageEntity = imageRepo.save(imageEntity);
        return new ImageResponse(imageEntity);
    }

    @Override
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

    @Override
    public void deleteImage(String email, Long imageId) throws EntityNotFoundException, IOException {
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
