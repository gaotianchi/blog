package com.gaotianchi.resource.web.service.imagecompressionservice;

import com.gaotianchi.resource.persistence.enums.CompressionLevel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface ImageCompressionServiceInterface {
    void compressImage(Map<CompressionLevel, Path> compressionPathMap) throws IOException;
}
