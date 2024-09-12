package com.gaotianchi.resource.web.service.imagecompressionservice;

import com.gaotianchi.resource.persistence.enums.CompressionLevel;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Slf4j
@Service
public class ImageCompressionService implements ImageCompressionServiceInterface {


    @Override
    public void compressImage(Map<CompressionLevel, Path> compressionPathMap) throws IOException {
        Path originalPath = compressionPathMap.get(CompressionLevel.ORIGINAL);
        for (CompressionLevel compressionLevel : CompressionLevel.values()) {
            try {
                Thumbnails.of(originalPath.toFile())
                        .scale(1.0)
                        .outputQuality(compressionLevel.getRate())
                        .toFile(compressionPathMap.get(compressionLevel).toFile());
            } catch (Exception e) {
                log.error("图片压缩失败 {} {}", e.getLocalizedMessage(), compressionPathMap.get(compressionLevel));
            }
        }
    }
}
