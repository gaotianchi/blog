package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.ImageEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadImageResult {
    private String originalUrl;
    private String thumbnailUrl;
    private String alt;
    public UploadImageResult(ImageEntity imageEntity) {
        this.alt = imageEntity.getAlt();
        this.originalUrl = imageEntity.getOriginalUrl();
        this.thumbnailUrl = imageEntity.getThumbnailUrl();
    }
}
