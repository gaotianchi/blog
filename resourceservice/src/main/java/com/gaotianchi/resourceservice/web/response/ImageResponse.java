package com.gaotianchi.resourceservice.web.response;

import com.gaotianchi.resourceservice.persistence.entity.ImageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponse {
    private Long id;
    private String fileDirName;
    private String fileExtension;
    private String originalUrl;
    private String thumbnailUrl;
    private String alt;

    public ImageResponse(ImageEntity imageEntity) {
        setupData(imageEntity);
    }

    private void setupData(ImageEntity imageEntity) {
        this.id = imageEntity.getId();
        this.fileDirName = imageEntity.getFileDirName();
        this.fileExtension = imageEntity.getFileExtension();
        this.originalUrl = imageEntity.getOriginalUrl();
        this.thumbnailUrl = imageEntity.getThumbnailUrl();
        this.alt = imageEntity.getAlt();
    }
}
