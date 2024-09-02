package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.ImageEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageOtd {
    private Long id;
    private String fileDirName;
    private String fileExtension;
    private String originalUrl;
    private String thumbnailUrl;
    private String alt;
    public ImageOtd(ImageEntity imageEntity) {
        this.id = imageEntity.getId();
        this.fileDirName = imageEntity.getFileDirName();
        this.fileExtension = imageEntity.getFileExtension();
        this.alt = imageEntity.getAlt();
        this.originalUrl = imageEntity.getOriginalUrl();
        this.thumbnailUrl = imageEntity.getThumbnailUrl();
    }
}
