package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.ArticleImageEntity;

public class UploadImage {
    private String originalUrl;
    private String thumbnailUrl;
    private String alt;
    public UploadImage(ArticleImageEntity articleImageEntity) {
        this.alt = articleImageEntity.getAlt();
        this.originalUrl = articleImageEntity.getOriginalUrl();
        this.thumbnailUrl = articleImageEntity.getThumbnailUrl();
    }
}
