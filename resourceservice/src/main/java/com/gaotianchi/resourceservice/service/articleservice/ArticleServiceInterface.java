package com.gaotianchi.resourceservice.service.articleservice;

import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.CommentResponse;
import com.gaotianchi.resourceservice.web.response.ImageResponse;
import com.gaotianchi.resourceservice.web.response.TagResponse;

import java.util.List;

public interface ArticleServiceInterface {
    ArticleResponse newArticle(String email);

    ArticleResponse publishArticle(String email, Long articleId);

    ArticleResponse setToDraft(String email, Long articleId);

    ArticleResponse setToTrash(String email, Long articleId);

    List<ArticleResponse> listArticles(String email);

    ArticleResponse updateContent(String email, Long articleId, String title, String body, String summary, String slug);

    ArticleResponse setSeries(String email, Long articleId, Long seriesId);

    void removeSeries(String email, Long articleId);

    ArticleResponse setCover(String email, Long articleId, Long coverId);

    void removeCover(String email, Long articleId);

    TagResponse addTag(String email, Long articleId, Long tagId);

    void removeTag(String email, Long tagId, Long articleId);

    List<TagResponse> listTags(Long id);

    List<CommentResponse> listArticleComments(Long articleId);

    ImageResponse addArticleImage(String email, Long articleId, Long imageId);

    void removeArticleImage(String email, Long articleId, Long imageId);

    List<ImageResponse> listArticleImages(Long articleId);

}
