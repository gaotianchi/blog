package com.gaotianchi.resource.web.service.series.cover;

import com.gaotianchi.resource.web.response.info.SeriesCoverInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SeriesCoverServiceInterface {
    SeriesCoverInfo newSeriesCover(String username, MultipartFile file) throws IOException;

    void deleteSeriesCover(String username, Long id) throws IOException;

    SeriesCoverInfo getInfo(Long id);
}
