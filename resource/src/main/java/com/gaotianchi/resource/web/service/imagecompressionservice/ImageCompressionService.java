package com.gaotianchi.resource.web.service.imagecompressionservice;

import com.gaotianchi.resource.persistence.enums.CompressionLevel;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class ImageCompressionService implements ImageCompressionServiceInterface {

    @Override
    public void compressImage(MultipartFile file, Path filepath, CompressionLevel level) {
        try {
            File outputFile = filepath.toFile();
            File parentDir = outputFile.getParentFile();

            if (!parentDir.exists()) {
                if (parentDir.mkdirs()) {
                    System.out.println("Parent directories created successfully.");
                } else {
                    System.out.println("Failed to create parent directories.");
                    throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
                }
            }
            if (level == CompressionLevel.ORIGINAL) {
                file.transferTo(outputFile);
            } else {
                Thumbnails.of(file.getInputStream())
                        .scale(1.0)
                        .outputQuality(level.getRate())
                        .toFile(outputFile);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error compressing image: " + e.getMessage(), e);
        }
    }
}
