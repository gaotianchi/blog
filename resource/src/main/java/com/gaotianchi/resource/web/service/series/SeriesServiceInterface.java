package com.gaotianchi.resource.web.service.series;

import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.SeriesCoverInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;

import java.io.IOException;

public interface SeriesServiceInterface {
    SeriesInfo newSeries(String username, String title, String profile);

    void deleteSeries(String username, Long id) throws IOException;

    void updateContent(String username, Long id, String newTitle, String newProfile);

    SeriesCoverInfo setCover(String username, Long id, Long newCoverId) throws IOException;

    void removeCover(String username, Long id) throws IOException;

    SeriesInfo getInfo(Long id);

    PageInfo<SeriesInfo> getUserSeriesInfoPage(Long userId, Integer page);
}
