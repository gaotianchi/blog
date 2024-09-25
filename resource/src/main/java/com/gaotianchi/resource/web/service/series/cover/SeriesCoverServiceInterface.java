package com.gaotianchi.resource.web.service.series.cover;

import com.gaotianchi.resource.web.response.PageSeriesCoverInfo;
import com.gaotianchi.resource.web.response.SeriesCoverInfo;
import org.springframework.web.multipart.MultipartFile;

public interface SeriesCoverServiceInterface {
    SeriesCoverInfo newSeriesCover(String username, MultipartFile file, Long id);

    void delete(String username, Long id);

    SeriesCoverInfo getInfo(String username, Long id);

    PageSeriesCoverInfo getPageInfo(String username);
}
