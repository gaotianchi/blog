package com.gaotianchi.resource.web.service.storage.cover;

import com.gaotianchi.resource.config.StorageConfig;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SeriesCoverStorageService implements SeriesCoverStorageServiceInterface {

    private final StorageConfig storageConfig;

    public SeriesCoverStorageService(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public void save(String filename, MultipartFile file) throws IOException {
        Path originalFilePath = getOriginalPath(filename);
        if (!originalFilePath.getParent().toFile().exists()) {
            if (!originalFilePath.getParent().toFile().mkdirs()) {
                throw new IOException("Fail to make dirs");
            }
        }
        file.transferTo(originalFilePath);
        BufferedImage originalImage = ImageIO.read(originalFilePath.toFile());
        int originalWidth = originalImage.getWidth();
        Path thumbnailFilePath = getThumbnailPath(filename);
        if (!thumbnailFilePath.getParent().toFile().exists()) {
            if (!thumbnailFilePath.getParent().toFile().mkdirs()) {
                throw new IOException("Fail to make dirs");
            }
        }
        int maxWidth = storageConfig.getSeriesCover().getMaxWidth();
        double quality = storageConfig.getSeriesCover().getQuality();
        if (originalWidth > maxWidth) {
            double targetRate = (double) maxWidth / originalWidth;
            Thumbnails.of(originalFilePath.toFile())
                    .scale(targetRate)
                    .outputQuality(quality)
                    .toFile(thumbnailFilePath.toFile());
        } else {
            Thumbnails.of(originalFilePath.toFile())
                    .scale(1.0F)
                    .outputQuality(quality)
                    .toFile(thumbnailFilePath.toFile());
        }
    }

    @Override
    public void delete(String filename) throws IOException {
        // 删除原始文件、缩略图以及文件夹
        Files.deleteIfExists(getOriginalPath(filename));
        Files.deleteIfExists(getThumbnailPath(filename));
    }

    @Override
    public Path getOriginalPath(String filename) {
        Path fileDir = Paths.get(storageConfig.getSeriesCover().getLocation()).normalize();
        return fileDir.resolve(storageConfig.getIllustration().getOriginalDirName()).resolve(filename).normalize();
    }

    @Override
    public Path getThumbnailPath(String filename) {
        Path fileDir = Paths.get(storageConfig.getSeriesCover().getLocation()).normalize();
        return fileDir.resolve(storageConfig.getIllustration().getThumbnailDirName()).resolve(filename).normalize();
    }
}
