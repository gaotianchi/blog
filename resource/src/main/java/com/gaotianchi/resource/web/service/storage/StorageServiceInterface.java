package com.gaotianchi.resource.web.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageServiceInterface {
    void save(String filename, MultipartFile file) throws IOException;

    void delete(String filename) throws IOException;

    Path getOriginalPath(String filename);
}
