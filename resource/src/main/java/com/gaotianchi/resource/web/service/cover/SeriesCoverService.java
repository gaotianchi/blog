package com.gaotianchi.resource.web.service.cover;

import com.gaotianchi.resource.web.response.PageSeriesCoverInfo;
import com.gaotianchi.resource.web.response.SeriesCoverInfo;
import org.springframework.web.multipart.MultipartFile;

public class SeriesCoverService implements SeriesCoverServiceInterface {
    @Override
    public SeriesCoverInfo newSeriesCover(String username, MultipartFile file, Long id) {
        return null;
    }

    @Override
    public void delete(String username, Long id) {

    }

    @Override
    public SeriesCoverInfo getInfo(String username, Long id) {
        return null;
    }

    @Override
    public PageSeriesCoverInfo getPageInfo(String username) {
        return null;
    }
}
