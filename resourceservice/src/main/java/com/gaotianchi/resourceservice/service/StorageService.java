package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.config.StorageProperties;
import com.gaotianchi.resourceservice.web.error.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

@Service
public class StorageService {
    private final StorageProperties storageProperties;

    @Autowired
    public StorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    public String store(MultipartFile file, Path relativePath) {
        try {
            if (file.isEmpty()) throw new FileNotFoundException();
            Path targetLocation = Paths.get(storageProperties.getRootLocation()).resolve(relativePath).normalize();
            if (Files.exists(targetLocation)) throw new FileAlreadyExistsException("文件" + targetLocation + "已经存在！");
            if (!Files.exists(targetLocation.getParent())) Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return getResourceUrl(relativePath);
        } catch (Exception e) {
            throw new StorageException();
        }
    }

    public void delete(Path relativePath) throws FileNotFoundException {
        Path targetLocation = Paths.get(storageProperties.getRootLocation()).resolve(relativePath).normalize();
        if (!Files.exists(targetLocation)) throw new FileNotFoundException();
        try {
            Files.delete(targetLocation);
        } catch (IOException e) {
            throw new StorageException();
        }
    }

    public String getResourceUrl(Path relativePath) {
        return storageProperties.getRootUrl() + "/" + relativePath.toString().replace("\\", "/");
    }
}