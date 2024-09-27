package com.gaotianchi.resource.web.service.Illustration;


import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.ArticleInfo;
import com.gaotianchi.resource.web.response.info.IllustrationInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IllustrationServiceInterface {
    IllustrationInfo newIllustration(String username, MultipartFile file, String title, String alt) throws IOException;

    void deleteIllustration(String username, Long id) throws IOException;

    void updateContent(String username, Long id, String newTitle, String newAlt);

    IllustrationInfo getInfo(Long id);

    PageInfo<IllustrationInfo> getPageInfo(String username, Integer page, boolean orderByCreationDatetime, boolean desc);

    List<ArticleInfo> getArticleList(String username, Long id);
}
