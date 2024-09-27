package com.gaotianchi.resource.web.service.cover;

import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.SeriesCoverInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SeriesCoverServiceInterface {
    SeriesCoverInfo newSeriesCover(String username, MultipartFile file) throws IOException;

    void delete(String username, Long id) throws IOException;

    SeriesCoverInfo getInfo(Long id);

    PageInfo<SeriesCoverInfo> getPageInfo(String username, int page);
}
