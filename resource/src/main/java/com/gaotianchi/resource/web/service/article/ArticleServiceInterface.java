package com.gaotianchi.resource.web.service.article;

import com.gaotianchi.resource.web.response.info.ArticleInfo;
import com.gaotianchi.resource.web.response.info.IllustrationInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;
import com.gaotianchi.resource.web.response.info.TagInfo;

import java.util.List;

public interface ArticleServiceInterface {

    ArticleInfo newArticle(String username);

    void deleteArticle(String username, Long id);

    ArticleInfo getInfo(String username, Long id);

    void updateStatus(String username, Long id, String newStatus) throws Exception;

    void updateTitle(String username, Long id, String newTitle);

    void updateSummary(String username, Long id, String newSummary);

    void updateSlug(String username, Long id, String newSlug);

    void updateBody(String username, Long id, String newBody);

    String getBody(String username, Long id);

    SeriesInfo setSeries(String username, Long id, Long seriesId);

    void removeSeries(String username, Long id);

    TagInfo addTag(String username, Long id, Long tagId);

    void removeTag(String username, Long tagId, Long id);

    List<TagInfo> getTagList(String username, Long id);

    IllustrationInfo addIllustration(String username, Long id, Long illustrationId);

    void removeIllustration(String username, Long id, Long illustrationId);
}
