package com.gaotianchi.resource.web.service.imagestorageservice;

import com.gaotianchi.resource.persistence.enums.CompressionLevel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface ImageStorageServiceInterface {
    Map<CompressionLevel, Path> save(HttpServletRequest req, MultipartFile file) throws IOException;

    void delete(Path srcPath);

}
