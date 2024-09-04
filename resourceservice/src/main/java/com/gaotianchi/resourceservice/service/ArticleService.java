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

    @Autowired
    public ArticleService(ArticleRepo articleRepo, UserRepo userRepo, CommentService commentService, SeriesRepo seriesRepo, ImageRepo imageRepo, TagRepo tagRepo, ArticleCacheService articleCacheService, EntityFounderService entityFounderService) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
        this.commentService = commentService;
        this.seriesRepo = seriesRepo;
        this.imageRepo = imageRepo;
        this.tagRepo = tagRepo;
        this.articleCacheService = articleCacheService;
        this.entityFounderService = entityFounderService;
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
        return new ArticleResponse(articleEntity);
    }

    public ArticleEntity createNewDraft(Long userId) throws UserNotFoundException {
        Optional<UserEntity> author = userRepo.findById(userId);
        if (author.isEmpty()) throw new UserNotFoundException();
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setAuthor(author.get());
        articleEntity = articleRepo.save(articleEntity);
        articleEntity.setSlug("Post_" + articleEntity.getId());
        articleEntity.setArticleStatus(ArticleStatus.DRAFT);
        articleEntity.setCreationDatetime(OffsetDateTime.now());
        articleEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        return articleRepo.save(articleEntity);
    }

    public ArticleEntity throwInTrashCan(Long articleId) throws ArticleNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        articleEntity.setArticleStatus(ArticleStatus.TRASH);
        return articleRepo.save(articleEntity);
    }

    public void deleteTrash(Long articleId) throws ArticleNotFoundException, UnExpectedStatusException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        if (articleEntity.getArticleStatus() != ArticleStatus.TRASH) throw new UnExpectedStatusException();
        articleRepo.delete(articleEntity);
    }

    public ArticleEntity publishDraft(Long articleId) throws ArticleNotFoundException, UnExpectedStatusException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        if (articleEntity.getArticleStatus() != ArticleStatus.DRAFT) throw new UnExpectedStatusException();
        articleEntity.setArticleStatus(ArticleStatus.PUBLISHED);
        articleEntity.setPublishDatetime(OffsetDateTime.now());
        return articleRepo.save(articleEntity);
    }


    public ArticleEntity convertToDraft(Long articleId) throws UnExpectedStatusException, ArticleNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        if (articleEntity.getArticleStatus() == ArticleStatus.DRAFT ) throw new UnExpectedStatusException();
        articleEntity.setArticleStatus(ArticleStatus.DRAFT);
        return articleRepo.save(articleEntity);
    }

    public ArticleEntity getArticleOrNotFound(Long articleId) throws ArticleNotFoundException {
        Optional<ArticleEntity> article = articleRepo.findById(articleId);
        if (article.isEmpty()) throw new ArticleNotFoundException();
        return article.get();
    }

    public ArticleEntity updateArticleContent(Long articleId, String title, String body, String summary, String slug) throws ArticleNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        articleEntity.setTitle(title);
        articleEntity.setSlug(slug);
        articleEntity.setBody(body);
        articleEntity.setSummary(summary);
        articleEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        return articleRepo.save(articleEntity);
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

    public void removeArticleSeries(Long id) throws ArticleNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(id);
        articleEntity.setSeriesEntity(null);
        articleRepo.save(articleEntity);
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
