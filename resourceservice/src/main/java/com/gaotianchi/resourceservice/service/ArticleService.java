package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.error.*;
import com.gaotianchi.resourceservice.persistence.entity.*;
import com.gaotianchi.resourceservice.persistence.enums.ArticleStatus;
import com.gaotianchi.resourceservice.persistence.repo.*;
import com.gaotianchi.resourceservice.web.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;
    private final UserRepo userRepo;
    private final CommentService commentService;
    private final SeriesRepo seriesRepo;
    private final ImageRepo imageRepo;
    private final TagRepo tagRepo;
    private final ArticleCacheService articleCacheService;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;

    @Autowired
    public ArticleService(ArticleRepo articleRepo, UserRepo userRepo, CommentService commentService, SeriesRepo seriesRepo, ImageRepo imageRepo, TagRepo tagRepo, ArticleCacheService articleCacheService, EntityFounderService entityFounderService, EntityBelongService entityBelongService) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
        this.commentService = commentService;
        this.seriesRepo = seriesRepo;
        this.imageRepo = imageRepo;
        this.tagRepo = tagRepo;
        this.articleCacheService = articleCacheService;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
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

    public ArticleResponse publishArticle(Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
        articleEntity.setArticleStatus(ArticleStatus.PUBLISHED);
        articleEntity.setPublishDatetime(OffsetDateTime.now());
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleResponse(articleEntity);
    }

    public ArticleResponse setToDraft(Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
        articleEntity.setArticleStatus(ArticleStatus.DRAFT);
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleResponse(articleEntity);
    }

    public ArticleResponse setToTrash(Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
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
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        if (!userEntity.getArticleEntities().contains(articleEntity))
            throw new EntityNotFoundException("Article " + articleId);
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

    public ArticleEntity getArticleOrNotFound(Long articleId) throws ArticleNotFoundException {
        Optional<ArticleEntity> article = articleRepo.findById(articleId);
        if (article.isEmpty()) throw new ArticleNotFoundException();
        return article.get();
    }


    public ArticleCommentsOtd getArticleCommentsOtd(Long id) throws CommentNotFoundException, ArticleNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(id);
        ArticleCommentsOtd articleCommentsOtd = new ArticleCommentsOtd();
        List<CommentWithRepliesOtd> commentWithRepliesOtds = new ArrayList<>();
        if (articleEntity.getCommentEntities().isEmpty()) {
            articleCommentsOtd.setCommentWithRepliesOtds(commentWithRepliesOtds);
            return articleCommentsOtd;
        }
        for (CommentEntity commentEntity : articleEntity.getCommentEntities()) {
            if (commentEntity.getParentComment() == null) {
                CommentWithRepliesOtd commentWithRepliesOtd = commentService.getCommentWithReplies(commentEntity);
                commentWithRepliesOtds.add(commentWithRepliesOtd);
            }
        }
        articleCommentsOtd.setCommentWithRepliesOtds(commentWithRepliesOtds);
        return articleCommentsOtd;
    }

    public SeriesEntity getSeriesOrNotFound(Long id) throws SeriesNotFoundException {
        Optional<SeriesEntity> seriesEntity = seriesRepo.findById(id);
        if (seriesEntity.isEmpty()) throw new SeriesNotFoundException();
        return seriesEntity.get();
    }

    public SeriesEntity updateArticleSeries(Long articleId, Long seriesId) throws SeriesNotFoundException, ArticleNotFoundException {
        SeriesEntity seriesEntity = getSeriesOrNotFound(seriesId);
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        articleEntity.setSeriesEntity(seriesEntity);
        articleRepo.save(articleEntity);
        return seriesEntity;
    }

    public ImageEntity getArticleImageOrNotFound(Long id) throws ImageNotFoundException {
        Optional<ImageEntity> articleImageEntity = imageRepo.findById(id);
        if (articleImageEntity.isEmpty()) throw new ImageNotFoundException();
        return articleImageEntity.get();
    }


    public ImageEntity updateArticleCover(Long articleId, Long coverId) throws ArticleNotFoundException, ImageNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        ImageEntity imageEntity = getArticleImageOrNotFound(coverId);
        articleEntity.setCover(imageEntity);
        articleRepo.save(articleEntity);
        return imageEntity;
    }
    public TagEntity getTagOrNotFound(Long id) throws TagNotFoundException {
        Optional<TagEntity> tagEntity = tagRepo.findById(id);
        if (tagEntity.isEmpty()) throw new TagNotFoundException();
        return tagEntity.get();
    }
    public void removeArticleTag(Long tagId, Long articleId) throws TagNotFoundException, ArticleNotFoundException {
        TagEntity tagEntity = getTagOrNotFound(tagId);
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        Collection<TagEntity> tagEntities = articleEntity.getTags();
        Collection<ArticleEntity> articleEntities = tagEntity.getArticles();
        articleEntities.remove(articleEntity);
        tagEntities.remove(tagEntity);
        articleEntity.setTags(tagEntities);
        articleRepo.save(articleEntity);
        tagRepo.save(tagEntity);
    }

    public TagEntity addArticleTag(Long articleId, Long tagId) throws TagNotFoundException, ArticleNotFoundException {
        TagEntity tagEntity = getTagOrNotFound(tagId);
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        Collection<TagEntity> tagEntities = articleEntity.getTags();
        Collection<ArticleEntity> articleEntities = tagEntity.getArticles();
        if (!tagEntities.contains(tagEntity)) tagEntities.add(tagEntity);
        if (!articleEntities.contains(articleEntity)) articleEntities.add(articleEntity);
        articleEntity.setTags(tagEntities);
        tagEntity.setArticles(articleEntities);
        articleRepo.save(articleEntity);
        tagRepo.save(tagEntity);
        return tagEntity;
    }

    public ArticleOtd getArticleOtd(ArticleEntity articleEntity) {
        ArticleOtd articleOtd = new ArticleOtd(articleEntity);
        articleOtd.setLike(articleCacheService.getNumberOfArticleLike(articleEntity.getId()));
        articleOtd.setDislike(articleCacheService.getNumberOfArticleDislike(articleEntity.getId()));
        return articleOtd;
    }


    public List<TagOtd> getArticleTags(Long id) throws ArticleNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(id);
        List<TagOtd> tagOtds = new ArrayList<>();
        for (TagEntity tagEntity : articleEntity.getTags()) {
            TagOtd tagOtd = new TagOtd(tagEntity);
            tagOtds.add(tagOtd);
        }
        return tagOtds;
    }
}
