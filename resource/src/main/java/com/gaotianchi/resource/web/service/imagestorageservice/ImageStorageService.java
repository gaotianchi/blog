package com.gaotianchi.resource.web.service.imagestorageservice;

import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.config.ImageConfig;
import com.gaotianchi.resource.persistence.enums.CompressionLevel;
import com.gaotianchi.resource.web.service.imagecompressionservice.ImageCompressionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageStorageService implements ImageStorageServiceInterface {
    private final ImageConfig imageConfig;
    private final ImageCompressionService imageCompressionService;

    public ImageStorageService(ImageConfig imageConfig, ImageCompressionService imageCompressionService) {
        this.imageConfig = imageConfig;
        this.imageCompressionService = imageCompressionService;
    }

    @Override
    public Map<String, String> save(HttpServletRequest req, MultipartFile file) {
        String filename = Utils.generateUniqueFileName() + Utils.getFileExtension(file.getOriginalFilename());
        Path rootPath = Paths.get(imageConfig.getStorage().getRootPath());
        Map<String, String> compressionLevelPathMap = new HashMap<>();
        for (CompressionLevel compressionLevel : CompressionLevel.values()) {
            String fullFileName = filename + File.separator + compressionLevel.name() + "." + filename;
            Path path = rootPath.resolve(fullFileName);
            imageCompressionService.compressImage(file, path, compressionLevel);
            compressionLevelPathMap.put(compressionLevel.name(), Utils.getImageUrl(req, fullFileName));
        }
        return compressionLevelPathMap;
    }

    @Override
    public void delete(Path srcPath) {

    }
}