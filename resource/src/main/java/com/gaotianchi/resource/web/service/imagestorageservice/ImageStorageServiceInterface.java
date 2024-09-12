package com.gaotianchi.resource.web.service.imagestorageservice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Map;

public interface ImageStorageServiceInterface {
    Map<String, String> save(HttpServletRequest req, MultipartFile file);

    void delete(Path srcPath);

}
