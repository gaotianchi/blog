package com.gaotianchi.resource.web.service.storage.illustration;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface IllustrationStorageServiceInterface {
    // 返回文件最终名称
    String save(MultipartFile file) throws IOException;

    void delete(String filename);

    Path getPath(String filename, boolean thumbnail);
}
