package com.gaotianchi.resource.web.service.series;

import com.gaotianchi.resource.web.response.info.SeriesCoverInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;
import com.gaotianchi.resource.web.response.page.PageSeriesInfo;

import java.io.IOException;

public interface SeriesServiceInterface {
    SeriesInfo newSeries(String username, String title, String profile);

    void updateContent(String username, Long id, String newTitle, String nProfile);

    void deleteSeries(String username, Long id) throws IOException;

    SeriesInfo getInfo(String username, Long id);

    PageSeriesInfo getPageInfo(String username, Integer page);

    SeriesCoverInfo setCover(String username, Long id, Long newCoverId);

    SeriesCoverInfo updateCover(String username, Long id, Long newCoverId) throws IOException;

    void removeCover(String username, Long id) throws IOException;
}
