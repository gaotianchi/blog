package com.gaotianchi.resourceservice.web.response;

import com.gaotianchi.resourceservice.persistence.entity.ArticleEntity;
import com.gaotianchi.resourceservice.persistence.entity.SeriesEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SeriesWithArticlesOtd extends SeriesOtd {
    private List<ArticleOtd> articleOtds = new ArrayList<>();
    public SeriesWithArticlesOtd(SeriesEntity seriesEntity) {
        super(seriesEntity);
        if (!seriesEntity.getArticleEntities().isEmpty()) {
            for (ArticleEntity articleEntity : seriesEntity.getArticleEntities()) {
                ArticleOtd articleOtd = new ArticleOtd(articleEntity);
                this.articleOtds.add(articleOtd);
            }
        }
    }
}
