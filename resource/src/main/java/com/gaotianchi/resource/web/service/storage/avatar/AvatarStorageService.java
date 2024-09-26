package com.gaotianchi.resource.web.service.storage.avatar;

import com.gaotianchi.resource.config.StorageConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AvatarStorageService implements AvatarStorageServiceInterface {

    private final StorageConfig storageConfig;

    public AvatarStorageService(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public void save(String filename, MultipartFile file) throws IOException {
        Path originalFilePath = getOriginalPath(filename);
        file.transferTo(originalFilePath);
    }

    @Override
    public void delete(String filename) throws IOException {
        Files.deleteIfExists(getOriginalPath(filename));
        Files.deleteIfExists(getOriginalPath(filename).getParent());
    }

    @Override
    public Path getOriginalPath(String filename) {
        return Paths.get(storageConfig.getAvatar().getLocation()).resolve(filename).normalize();
    }
}
