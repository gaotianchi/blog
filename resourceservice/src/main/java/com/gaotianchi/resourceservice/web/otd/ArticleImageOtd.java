package com.gaotianchi.resourceservice.web.otd;

import com.gaotianchi.resourceservice.entity.ImageEntity;
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
