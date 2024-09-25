package com.gaotianchi.resource.web.service.storage.illustration;

import com.gaotianchi.resource.Utils;
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
    public String save(MultipartFile file) throws IOException {
        String filename = Utils.generateUniqueFileName() + Utils.getFileExtension(file.getOriginalFilename());
        // 存储原始文件
        Path originalFilePath = getPath(filename, false);
        file.transferTo(originalFilePath);

        // 压缩原始文件获取缩略图
        BufferedImage originalImage = ImageIO.read(originalFilePath.toFile());
        int originalWidth = originalImage.getWidth();
        Path thumbnailFilePath = getPath(filename, true);
        if (originalWidth > 300) {
            double targetRate = (double) 300 / originalWidth;
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
        return filename;
    }

    @Override
    public void delete(String filename) throws IOException {
        // 删除原始文件、缩略图以及文件夹
        Files.deleteIfExists(getPath(filename, false));
        Files.deleteIfExists(getPath(filename, true));
        Files.deleteIfExists(getPath(filename, true).getParent());
    }

    @Override
    public Path getPath(String filename, boolean thumbnail) {
        Path fileDir = Paths.get(storageConfig.getIllustration().getLocation()).resolve(filename).normalize();
        if (thumbnail) {
            return fileDir.resolve(storageConfig.getIllustration().getThumbnailPrefix() + filename);
        }
        return fileDir.resolve(storageConfig.getIllustration().getOriginalPrefix() + filename);
    }
}
