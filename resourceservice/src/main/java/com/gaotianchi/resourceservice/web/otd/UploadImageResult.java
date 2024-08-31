package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.ArticleImageEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadImageResult {
    private String originalUrl;
    private String thumbnailUrl;
    private String alt;
    public UploadImageResult(ArticleImageEntity articleImageEntity) {
        this.alt = articleImageEntity.getAlt();
        this.originalUrl = articleImageEntity.getOriginalUrl();
        this.thumbnailUrl = articleImageEntity.getThumbnailUrl();
    }
}
