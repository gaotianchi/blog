package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.persistence.entity.*;
import com.gaotianchi.resourceservice.persistence.enums.ArticleStatus;
import com.gaotianchi.resourceservice.persistence.repo.ArticleRepo;
import com.gaotianchi.resourceservice.persistence.repo.ImageRepo;
import com.gaotianchi.resourceservice.persistence.repo.TagRepo;
import com.gaotianchi.resourceservice.web.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.CommentResponse;
import com.gaotianchi.resourceservice.web.response.ImageResponse;
import com.gaotianchi.resourceservice.web.response.TagResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;
    private final CommentService commentService;
    private final TagRepo tagRepo;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;
    private final ImageRepo imageRepo;

    @Autowired
    public ArticleService(ArticleRepo articleRepo, CommentService commentService, TagRepo tagRepo, EntityFounderService entityFounderService, EntityBelongService entityBelongService, ImageRepo imageRepo) {
        this.articleRepo = articleRepo;
        this.commentService = commentService;
        this.tagRepo = tagRepo;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
        this.imageRepo = imageRepo;
    }

    public ArticleResponse newArticle(String email) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setAuthor(userEntity);
        articleEntity = articleRepo.save(articleEntity);
        articleEntity.setSlug("Post_" + articleEntity.getId());
        articleEntity.setArticleStatus(ArticleStatus.DRAFT);
        articleEntity.setCreationDatetime(OffsetDateTime.now());
        articleEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleResponse(articleEntity);
    }

    public ArticleResponse publishArticle(String email, Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        articleEntity.setArticleStatus(ArticleStatus.PUBLISHED);
        articleEntity.setPublishDatetime(OffsetDateTime.now());
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleResponse(articleEntity);
    }

    public ArticleResponse setToDraft(String email, Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        articleEntity.setArticleStatus(ArticleStatus.DRAFT);
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleResponse(articleEntity);
    }

    public ArticleResponse setToTrash(String email, Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        articleEntity.setArticleStatus(ArticleStatus.TRASH);
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleResponse(articleEntity);
    }

    public List<ArticleResponse> listArticles(String email) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        Collection<ArticleEntity> articleEntities = userEntity.getArticleEntities();
        return articleEntities.stream()
                .map(ArticleResponse::new)
                .collect(Collectors.toList());
    }

    public ArticleResponse updateContent(String email, Long articleId, String title, String body, String summary, String slug) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        articleEntity.setTitle(title);
        articleEntity.setSlug(slug);
        articleEntity.setBody(body);
        articleEntity.setSummary(summary);
        articleEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleResponse(articleEntity);
    }

    public ArticleResponse setSeries(String email, Long articleId, Long seriesId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(email, seriesId);
        articleEntity.setSeriesEntity(seriesEntity);
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleResponse(articleEntity, true);
    }

    public void removeSeries(String email, Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        articleEntity.setSeriesEntity(null);
        articleRepo.save(articleEntity);
    }

    public ArticleResponse setCover(String email, Long articleId, Long coverId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        ImageEntity imageEntity = entityBelongService.imageBelongToUser(email, coverId);
        articleEntity.setCover(imageEntity);
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleResponse(articleEntity, false, true);
    }

    public void removeCover(String email, Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        articleEntity.setCover(null);
        articleRepo.save(articleEntity);
    }

    public TagResponse addTag(String email, Long articleId, Long tagId) throws EntityNotFoundException {
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        Collection<TagEntity> tagEntities = articleEntity.getTags();
        Collection<ArticleEntity> articleEntities = tagEntity.getArticles();
        if (!tagEntities.contains(tagEntity)) tagEntities.add(tagEntity);
        if (!articleEntities.contains(articleEntity)) articleEntities.add(articleEntity);
        articleEntity.setTags(tagEntities);
        tagEntity.setArticles(articleEntities);
        articleRepo.save(articleEntity);
        tagEntity = tagRepo.save(tagEntity);
        return new TagResponse(tagEntity);
    }

    public void removeTag(String email, Long tagId, Long articleId) throws EntityNotFoundException {
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        Collection<TagEntity> tagEntities = articleEntity.getTags();
        Collection<ArticleEntity> articleEntities = tagEntity.getArticles();
        articleEntities.remove(articleEntity);
        tagEntities.remove(tagEntity);
        articleEntity.setTags(tagEntities);
        articleRepo.save(articleEntity);
        tagRepo.save(tagEntity);
    }

    public List<TagResponse> listTags(Long id) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(id);
        List<TagResponse> tagResponses = new ArrayList<>();
        for (TagEntity tagEntity : articleEntity.getTags()) {
            TagResponse tagResponse = new TagResponse(tagEntity);
            tagResponses.add(tagResponse);
        }
        return tagResponses;
    }

    public List<CommentResponse> listArticleComments(Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (CommentEntity commentEntity : articleEntity.getCommentEntities()) {
            if (commentEntity.getParentComment() == null) {
                commentResponses.add(commentService.getCommentTree(commentEntity.getId()));
            }
        }
        return commentResponses;
    }

    public ImageResponse addArticleImage(String email, Long articleId, Long imageId) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(userEntity, articleId);
        ImageEntity imageEntity = entityBelongService.imageBelongToUser(userEntity, imageId);
        if (articleEntity.getArticleImages().add(imageEntity)) {
            articleRepo.save(articleEntity);
        }
        if (imageEntity.getArticles().add(articleEntity)) {
            imageRepo.save(imageEntity);
        }
        return new ImageResponse(imageEntity);
    }

    public void removeArticleImage(String email, Long articleId, Long imageId) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(userEntity, articleId);
        ImageEntity imageEntity = entityBelongService.imageBelongToUser(userEntity, imageId);
        if (articleEntity.getArticleImages().remove(imageEntity)) {
            articleRepo.save(articleEntity);
        }
        if (imageEntity.getArticles().remove(articleEntity)) {
            imageRepo.save(imageEntity);
        }
    }

    public List<ImageResponse> listArticleImages(Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
        return articleEntity.getArticleImages().stream().map(ImageResponse::new).collect(Collectors.toList());
    }
}
