package com.gaotianchi.resourceservice.service.tagservice;

import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.TagResponse;

import java.util.List;

public interface TagServiceInterface {
    TagResponse newTag(String name);

    List<TagResponse> listTags();

    void deleteTag(Long id);

    List<ArticleResponse> listArticles(Long id);

    TagResponse setArticle(String email, Long tagId, Long articleId);
}
