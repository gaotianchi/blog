package com.gaotianchi.resource.web.service.cover;

import com.gaotianchi.resource.web.response.PageSeriesCoverInfo;
import com.gaotianchi.resource.web.response.SeriesCoverInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SeriesCoverServiceInterface {
    SeriesCoverInfo newSeriesCover(String username, MultipartFile file) throws IOException;

    void delete(String username, Long id) throws IOException;

    SeriesCoverInfo getInfo(String username, Long id);

    PageSeriesCoverInfo getPageInfo(String username, int page);
}
