package com.gaotianchi.resourceservice.web.response;

import com.gaotianchi.resourceservice.persistence.entity.ImageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleImageOtd extends ImageOtd{

    private ArticleOtd articleOtd;
    public ArticleImageOtd(ImageEntity imageEntity) {
        super(imageEntity);
        this.articleOtd = new ArticleOtd(imageEntity.getArticle());
    }
}
