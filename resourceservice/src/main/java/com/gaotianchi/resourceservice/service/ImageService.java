package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.config.StorageProperties;
import com.gaotianchi.resourceservice.entity.ArticleEntity;
import com.gaotianchi.resourceservice.entity.ImageEntity;
import com.gaotianchi.resourceservice.enums.ArticleImageType;
import com.gaotianchi.resourceservice.repo.ImageRepo;
import com.gaotianchi.resourceservice.repo.ArticleRepo;
import com.gaotianchi.resourceservice.web.error.ImageNotFoundException;
import com.gaotianchi.resourceservice.web.error.ArticleNotFoundException;
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
import java.util.Optional;
import java.util.Random;

@Service
public class ImageService {

    private final ArticleRepo articleRepo;
    private final StorageService storageService;
    private final ImageRepo imageRepo;
    private final StorageProperties storageProperties;
    private final CompressionService compressionService;

    @Autowired
    public ImageService(ArticleRepo articleRepo, StorageService storageService, ImageRepo imageRepo, StorageProperties storageProperties, CompressionService compressionService) {
        this.articleRepo = articleRepo;
        this.storageService = storageService;
        this.imageRepo = imageRepo;
        this.storageProperties = storageProperties;
        this.compressionService = compressionService;
    }

    public ImageEntity createArticleImage(MultipartFile file, Long articleId) throws IOException, ArticleNotFoundException {
        ImageEntity imageEntity = saveImage(file);
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        imageEntity.setArticle(articleEntity);
        return imageRepo.save(imageEntity);
    }

    public ImageEntity createImage(MultipartFile file) throws IOException {
        ImageEntity imageEntity = saveImage(file);
        return imageRepo.save(imageEntity);
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


    public void deleteImage(Long imageId) throws ImageNotFoundException, IOException {
        ImageEntity imageEntity = getArticleImageOrNotFound(imageId);
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


    public ArticleEntity getArticleOrNotFound(Long  articleId) throws ArticleNotFoundException {
        Optional<ArticleEntity> articleEntity = articleRepo.findById(articleId);
        if (articleEntity.isEmpty()) throw new ArticleNotFoundException();
        return articleEntity.get();
    }

    public ImageEntity getArticleImageOrNotFound(Long id) throws ImageNotFoundException {
        Optional<ImageEntity> articleImageEntity = imageRepo.findById(id);
        if (articleImageEntity.isEmpty()) throw new ImageNotFoundException();
        return articleImageEntity.get();
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
}
