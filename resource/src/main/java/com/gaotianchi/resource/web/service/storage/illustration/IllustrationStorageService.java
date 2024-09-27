package com.gaotianchi.resource.web.service.storage.illustration;

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
public class IllustrationStorageService implements IllustrationStorageServiceInterface {
    private final StorageConfig storageConfig;

    public IllustrationStorageService(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public void save(String filename, MultipartFile file) throws IOException {
        // 存储原始文件
        Path originalFilePath = getOriginalPath(filename);
        if (!originalFilePath.getParent().toFile().exists()) {
            if (!originalFilePath.getParent().toFile().mkdirs()) {
                throw new IOException("Fail to make dirs");
            }
        }
        file.transferTo(originalFilePath);

        // 压缩原始文件获取缩略图
        BufferedImage originalImage = ImageIO.read(originalFilePath.toFile());
        int originalWidth = originalImage.getWidth();
        Path thumbnailFilePath = getThumbnailPath(filename);
        if (!thumbnailFilePath.getParent().toFile().exists()) {
            if (!thumbnailFilePath.getParent().toFile().mkdirs()) {
                throw new IOException("Fail to make dirs");
            }
        }
        int maxWidth = storageConfig.getIllustration().getMaxWidth();
        double quality = storageConfig.getIllustration().getQuality();
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
        Path fileDir = Paths.get(storageConfig.getIllustration().getLocation()).resolve(filename).normalize();
        return fileDir.resolve(storageConfig.getIllustration().getOriginalDirName()).resolve(filename).normalize();
    }

    @Override
    public Path getThumbnailPath(String filename) {
        Path fileDir = Paths.get(storageConfig.getIllustration().getLocation()).resolve(filename).normalize();
        return fileDir.resolve(storageConfig.getIllustration().getThumbnailDirName()).resolve(filename).normalize();
    }
}
