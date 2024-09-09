package com.gaotianchi.resource.web.service.tagservice;

import com.gaotianchi.resource.web.response.ArticleResponse;
import com.gaotianchi.resource.web.response.TagResponse;

import java.util.List;

public interface TagServiceInterface {
    TagResponse newTag(String name);

    List<TagResponse> listTags();

    void deleteTag(Long id);

    List<ArticleResponse> listArticles(Long id);

    TagResponse setArticle(String email, Long tagId, Long articleId);
}
