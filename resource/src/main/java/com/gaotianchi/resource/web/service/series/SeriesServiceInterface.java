package com.gaotianchi.resource.web.service.series;

import com.gaotianchi.resource.web.response.ArticleInfo;
import com.gaotianchi.resource.web.response.PageSeriesInfo;
import com.gaotianchi.resource.web.response.SeriesCoverInfo;
import com.gaotianchi.resource.web.response.SeriesInfo;

public interface SeriesServiceInterface {
    SeriesInfo newSeries(String username, String title, String profile);

    void updateContent(String username, Long id, String newTitle, String nProfile);

    void deleteSeries(String username, Long id);

    // Content
    SeriesInfo getInfo(String username, Long id);

    PageSeriesInfo getPageInfo(String username, Integer page);

    // Cover
    SeriesCoverInfo setCover(String username, Long id, Long coverId);

    SeriesCoverInfo updateCover(String username, Long id, Long oldCoverId, Long newCoverId);

    void removeCover(String username, Long id, Long coverId);

    // Article
    ArticleInfo addArticle(String username, Long id, Long articleId);

    void removeArticle(String username, Long id, Long articleId);
}
