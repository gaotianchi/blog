package com.gaotianchi.resource.event;

import com.gaotianchi.resource.web.service.imagecompressionservice.ImageCompressionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ImageSavedListener implements ApplicationListener<ImageSavedEvent> {

    private final ImageCompressionService imageCompressionService;

    public ImageSavedListener(ImageCompressionService imageCompressionService) {
        this.imageCompressionService = imageCompressionService;
    }

    @Override
    public void onApplicationEvent(ImageSavedEvent imageSavedEvent) {
        try {
            imageCompressionService.compressImage(imageSavedEvent.getCompressionPathMap());
        } catch (IOException e) {
            log.error("图片压缩失败！{}", e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}
