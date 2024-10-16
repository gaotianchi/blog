package com.gaotianchi.resource.web.service.article;

import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.ArticleInfo;
import com.gaotianchi.resource.web.response.info.IllustrationInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;
import com.gaotianchi.resource.web.response.info.TagInfo;

import java.util.List;

public interface ArticleServiceInterface {

    // 创建与删除资源
    ArticleInfo newArticle(String username);

    void deleteArticle(String username, Long id);

    // 更新实体的浅层数据
    void updateStatus(String username, Long id, String newStatus) throws Exception;

    void updateContent(String username, Long id, String newTitle, String newBody);

    void updateSummary(String username, Long id, String newSummary);

    void updateSlug(String username, Long id, String newSlug);

    SeriesInfo setSeries(String username, Long id, Long newSeriesId);

    void removeSeries(String username, Long id);

    TagInfo addTag(String username, Long id, Long tagId);

    void removeTag(String username, Long tagId, Long id);

    IllustrationInfo addIllustration(String username, Long id, Long illustrationId);

    void removeIllustration(String username, Long id, Long illustrationId);

    void resetIllustrationList(String username, Long id, List<Long> illustrationIds);

    ArticleInfo getInfo(Long id);

    String getBody(Long id);

    PageInfo<ArticleInfo> getUserArticleInfoPage(Long userId, String status, int page);

    PageInfo<ArticleInfo> getSeriesArticleInfoPage(Long seriesId, int page);

    PageInfo<ArticleInfo> getTagArticleInfoPage(Long tagId, int page);

    PageInfo<ArticleInfo> getIllustrationArticleInfoPage(Long illustrationId, int page);

    PageInfo<ArticleInfo> getCommentArticleInfoPage(Long commentId, int page);
}
