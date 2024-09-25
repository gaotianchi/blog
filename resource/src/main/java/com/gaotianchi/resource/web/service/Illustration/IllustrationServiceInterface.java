package com.gaotianchi.resource.web.service.Illustration;


import com.gaotianchi.resource.web.response.ArticleInfo;
import com.gaotianchi.resource.web.response.IllustrationInfo;
import com.gaotianchi.resource.web.response.PageIllustrationInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IllustrationServiceInterface {
    IllustrationInfo newIllustration(String username, MultipartFile file, String title, String alt);

    void deleteIllustration(String username, Long id);

    void updateContent(String username, String title, String alt);

    IllustrationInfo getInfo(String username, Long id);

    PageIllustrationInfo getPageInfo(String username, Integer page);

    List<ArticleInfo> getArticleList(String username, Long id);
}
