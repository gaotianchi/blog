package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.entity.*;
import com.gaotianchi.resourceservice.enums.ArticleStatus;
import com.gaotianchi.resourceservice.repo.*;
import com.gaotianchi.resourceservice.web.error.*;
import com.gaotianchi.resourceservice.web.otd.ArticleCommentsOtd;
import com.gaotianchi.resourceservice.web.otd.ArticleOtd;
import com.gaotianchi.resourceservice.web.otd.CommentWithRepliesOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final ArticleImageRepo articleImageRepo;
    private final TagRepo tagRepo;
    private final ArticleCacheService articleCacheService;

    @Autowired
    public ArticleService(ArticleRepo articleRepo, UserRepo userRepo, CommentService commentService, SeriesRepo seriesRepo, ArticleImageRepo articleImageRepo, TagRepo tagRepo, ArticleCacheService articleCacheService) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
        this.commentService = commentService;
        this.seriesRepo = seriesRepo;
        this.articleImageRepo = articleImageRepo;
        this.tagRepo = tagRepo;
        this.articleCacheService = articleCacheService;
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

    public ArticleImageEntity getArticleImageOrNotFound(Long id) throws ArticleImageNotFoundException {
        Optional<ArticleImageEntity> articleImageEntity = articleImageRepo.findById(id);
        if (articleImageEntity.isEmpty()) throw new ArticleImageNotFoundException();
        return articleImageEntity.get();
    }


    public ArticleImageEntity updateArticleCover(Long articleId, Long coverId) throws ArticleNotFoundException, ArticleImageNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        ArticleImageEntity articleImageEntity = getArticleImageOrNotFound(coverId);
        articleEntity.setCover(articleImageEntity);
        articleRepo.save(articleEntity);
        return articleImageEntity;
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
        tagEntities.remove(tagEntity);
        articleEntity.setTags(tagEntities);
        articleRepo.save(articleEntity);
    }

    public TagEntity addArticleTag(Long articleId, Long tagId) throws TagNotFoundException, ArticleNotFoundException {
        TagEntity tagEntity = getTagOrNotFound(tagId);
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        Collection<TagEntity> tagEntities = articleEntity.getTags();
        if (!tagEntities.contains(tagEntity)) tagEntities.add(tagEntity);
        articleEntity.setTags(tagEntities);
        articleRepo.save(articleEntity);
        return tagEntity;
    }

    public ArticleOtd getArticleOtd(ArticleEntity articleEntity) {
        ArticleOtd articleOtd = new ArticleOtd(articleEntity);
        articleOtd.setLike(articleCacheService.getNumberOfArticleLike(articleEntity.getId()));
        articleOtd.setDislike(articleCacheService.getNumberOfArticleDislike(articleEntity.getId()));
        return articleOtd;
    }
}
