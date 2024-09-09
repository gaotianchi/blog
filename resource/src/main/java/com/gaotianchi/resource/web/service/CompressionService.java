package com.gaotianchi.resource.web.service;

import com.gaotianchi.resource.config.StorageProperties;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CompressionService {
    private final StorageProperties storageProperties;

    @Autowired
    public CompressionService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    public String compress(Path srcRelativePath, Path dstRelativePath) throws IOException {
        Thumbnails.of(Paths.get(storageProperties.getRootLocation()).resolve(srcRelativePath).toFile()).scale(0.25f).outputQuality(0.25f).toFile(Paths.get(storageProperties.getRootLocation()).resolve(dstRelativePath).toFile());
        return getResourceUrl(dstRelativePath);
    }

    public String getResourceUrl(Path relativePath) {
        return storageProperties.getRootUrl() + "/" + relativePath.toString().replace("\\", "/");
    }

}
