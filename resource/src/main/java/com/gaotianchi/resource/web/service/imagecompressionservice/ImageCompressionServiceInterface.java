package com.gaotianchi.resource.web.service.imagecompressionservice;

import com.gaotianchi.resource.persistence.enums.CompressionLevel;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface ImageCompressionServiceInterface {
    void compressImage(MultipartFile file, Path filepath, CompressionLevel level);
}
