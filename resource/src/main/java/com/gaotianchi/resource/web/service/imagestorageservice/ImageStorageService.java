package com.gaotianchi.resource.web.service.imagestorageservice;

import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.config.ImageConfig;
import com.gaotianchi.resource.event.ImageSavedEvent;
import com.gaotianchi.resource.persistence.enums.CompressionLevel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ImageStorageService implements ImageStorageServiceInterface {
    private final ImageConfig imageConfig;
    private final ApplicationContext applicationContext;

    public ImageStorageService(ImageConfig imageConfig, ApplicationContext applicationContext) {
        this.imageConfig = imageConfig;
        this.applicationContext = applicationContext;
    }

    @Override
    public Map<CompressionLevel, Path> save(HttpServletRequest req, MultipartFile file) throws IOException {
        String filename = Utils.generateUniqueFileName() + Utils.getFileExtension(file.getOriginalFilename());
        Path originalFilePath = Utils.getImageFullPath(imageConfig.getStorage().getRootPath(), filename, CompressionLevel.ORIGINAL);
        File imageDir = originalFilePath.getParent().toFile();
        if (!imageDir.exists()) {
            if (imageDir.mkdirs()) {
                log.debug("初始化图片文件夹。");
            } else {
                throw new IOException("文件夹 {} 创建失败！" + imageDir);
            }
        }
        file.transferTo(originalFilePath);
        Map<CompressionLevel, Path> compressionLevelPathMap = new HashMap<>();
        for (CompressionLevel compressionLevel : CompressionLevel.values()) {
            compressionLevelPathMap.put(compressionLevel, Utils.getImageFullPath(imageConfig.getStorage().getRootPath(), filename, compressionLevel));
        }
        applicationContext.publishEvent(new ImageSavedEvent(this, compressionLevelPathMap));
        return compressionLevelPathMap;
    }

    @Override
    public void delete(Path srcPath) {

    }
}