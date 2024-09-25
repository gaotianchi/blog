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
        Path originalFilePath = getPath(filename, false);
        file.transferTo(originalFilePath);
        BufferedImage originalImage = ImageIO.read(originalFilePath.toFile());
        int originalWidth = originalImage.getWidth();
        Path thumbnailFilePath = getPath(filename, true);
        if (originalWidth > 600) {
            double targetRate = (double) 600 / originalWidth;
            Thumbnails.of(originalFilePath.toFile())
                    .scale(targetRate)
                    .outputQuality(0.5F)
                    .toFile(thumbnailFilePath.toFile());
        } else {
            Thumbnails.of(originalFilePath.toFile())
                    .scale(1.0F)
                    .outputQuality(0.5F)
                    .toFile(thumbnailFilePath.toFile());
        }
    }

    @Override
    public void delete(String filename) throws IOException {
        // 删除原始文件、缩略图以及文件夹
        Files.deleteIfExists(getPath(filename, false));
        Files.deleteIfExists(getPath(filename, true));
        Files.deleteIfExists(getPath(filename, true).getParent());
    }

    @Override
    public Path getPath(String filename) {
        Path fileDir = Paths.get(storageConfig.getSeriesCover().getLocation()).resolve(filename).normalize();
        return fileDir.resolve(storageConfig.getSeriesCover().getOriginalPrefix() + filename);
    }

    @Override
    public Path getPath(String filename, boolean thumbnail) {
        Path fileDir = Paths.get(storageConfig.getSeriesCover().getLocation()).resolve(filename).normalize();
        if (thumbnail) {
            return fileDir.resolve(storageConfig.getSeriesCover().getThumbnailPrefix() + filename);
        }
        return fileDir.resolve(storageConfig.getSeriesCover().getOriginalPrefix() + filename);
    }
}
