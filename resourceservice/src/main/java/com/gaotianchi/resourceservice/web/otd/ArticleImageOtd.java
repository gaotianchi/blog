package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.ArticleImageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleImageOtd {
    private Long id;
    private String fileDirName;
    private String fileExtension;
    private String originalUrl;
    private String thumbnailUrl;
    private String alt;
    private ArticleOtd articleOtd;
    public ArticleImageOtd(ArticleImageEntity articleImageEntity) {
        this.id = articleImageEntity.getId();
        this.fileDirName = articleImageEntity.getFileDirName();
        this.fileExtension = articleImageEntity.getFileExtension();
        this.alt = articleImageEntity.getAlt();
        this.originalUrl = articleImageEntity.getOriginalUrl();
        this.thumbnailUrl = articleImageEntity.getThumbnailUrl();
        this.articleOtd = new ArticleOtd(articleImageEntity.getArticle());
    }
}
