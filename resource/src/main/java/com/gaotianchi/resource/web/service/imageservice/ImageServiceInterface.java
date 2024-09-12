package com.gaotianchi.resource.web.service.imageservice;

import com.gaotianchi.resource.web.response.ImageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageServiceInterface {
    ImageResponse newImage(HttpServletRequest req, MultipartFile file, String username) throws IOException;

    List<ImageResponse> listImages(String username);

    void deleteImage(String username, Long imageId) throws IOException;
}
