package com.gaotianchi.resource.web.service.storage.illustration;

import com.gaotianchi.resource.web.service.storage.StorageServiceInterface;

import java.nio.file.Path;

public interface IllustrationStorageServiceInterface extends StorageServiceInterface {
    Path getPath(String filename, boolean thumbnail);
}
