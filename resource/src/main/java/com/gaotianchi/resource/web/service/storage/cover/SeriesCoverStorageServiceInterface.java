package com.gaotianchi.resource.web.service.storage.cover;

import com.gaotianchi.resource.web.service.storage.StorageServiceInterface;

import java.nio.file.Path;

public interface SeriesCoverStorageServiceInterface extends StorageServiceInterface {
    Path getThumbnailPath(String filename);
}
