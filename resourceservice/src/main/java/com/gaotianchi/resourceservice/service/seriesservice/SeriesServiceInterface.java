package com.gaotianchi.resourceservice.service.seriesservice;

import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.SeriesResponse;

import java.util.List;

public interface SeriesServiceInterface {
    SeriesResponse newSeries(String email, String name, Long coverId);

    List<SeriesResponse> listSeries(String email);

    List<ArticleResponse> listArticles(String email, Long seriesId);

    void deleteSeries(String email, Long seriesId);
}
