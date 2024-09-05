package com.gaotianchi.resourceservice.service.imageservice;

import com.gaotianchi.resourceservice.web.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageServiceInterface {
    ImageResponse newImage(MultipartFile file, String email) throws IOException;

    List<ImageResponse> listImages(String email);

    void deleteImage(String email, Long imageId) throws IOException;
}
