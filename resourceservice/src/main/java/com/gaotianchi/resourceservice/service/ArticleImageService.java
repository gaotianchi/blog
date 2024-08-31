package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.config.StorageProperties;
import com.gaotianchi.resourceservice.entity.ArticleEntity;
import com.gaotianchi.resourceservice.entity.ArticleImageEntity;
import com.gaotianchi.resourceservice.enums.ArticleImageType;
import com.gaotianchi.resourceservice.repo.ArticleImageRepo;
import com.gaotianchi.resourceservice.repo.ArticleRepo;
import com.gaotianchi.resourceservice.web.error.ArticleImageNotFoundException;
import com.gaotianchi.resourceservice.web.error.ArticleNotFoundException;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
public class ArticleImageService {

    private final ArticleRepo articleRepo;
    private final StorageService storageService;
    private final ArticleImageRepo articleImageRepo;
    private final StorageProperties storageProperties;
    private final CompressionService compressionService;

    @Autowired
    public ArticleImageService(ArticleRepo articleRepo, StorageService storageService, ArticleImageRepo articleImageRepo, StorageProperties storageProperties, CompressionService compressionService) {
        this.articleRepo = articleRepo;
        this.storageService = storageService;
        this.articleImageRepo = articleImageRepo;
        this.storageProperties = storageProperties;
        this.compressionService = compressionService;
    }

    public ArticleImageEntity createArticleImage(MultipartFile file, Long articleId) throws IOException, ArticleNotFoundException {
        if (file.isEmpty()) throw new FileNotFoundException();
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        String fileDirName = generateUniqueFileName();
        String fileExtension = getFileExtension(file);
        Path thumbnailRelativePath = Paths.get(storageProperties.getArticleImageUri()).resolve(fileDirName).resolve(ArticleImageType.THUMBNAIL.name() + fileExtension);
        Path originalRelativePath = Paths.get(storageProperties.getArticleImageUri()).resolve(fileDirName).resolve(ArticleImageType.ORIGINAL.name() + fileExtension);
        String originalUrl = storeOriginalArticleImage(file, originalRelativePath);
        String thumbnailUrl = storeThumbnailArticleImage(originalRelativePath, thumbnailRelativePath);
        ArticleImageEntity articleImageEntity = new ArticleImageEntity();
        articleImageEntity.setFileDirName(fileDirName);
        articleImageEntity.setArticle(articleEntity);
        articleImageEntity.setOriginalUrl(originalUrl);
        articleImageEntity.setFileExtension(fileExtension);
        articleImageEntity.setThumbnailUrl(thumbnailUrl);
        return articleImageRepo.save(articleImageEntity);
    }

    public void deleteImage(Long imageId) throws ArticleImageNotFoundException, IOException {
        ArticleImageEntity articleImageEntity = getArticleImageOrNotFound(imageId);
        Path fileDirPath = findArticleImageByDirName(articleImageEntity.getFileDirName());
        deleteImages(fileDirPath, articleImageEntity.getFileExtension());
        articleImageRepo.delete(articleImageEntity);
    }

    public Path findArticleImageByDirName(String fileDirName) {
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

    public ArticleImageEntity getArticleImageOrNotFound(Long id) throws ArticleImageNotFoundException {
        Optional<ArticleImageEntity> articleImageEntity = articleImageRepo.findById(id);
        if (articleImageEntity.isEmpty()) throw new ArticleImageNotFoundException();
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
