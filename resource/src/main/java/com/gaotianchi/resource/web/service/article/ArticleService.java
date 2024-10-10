package com.gaotianchi.resource.web.service.article;

import com.gaotianchi.resource.config.PaginationConfig;
import com.gaotianchi.resource.persistence.entity.*;
import com.gaotianchi.resource.persistence.enums.ArticleStatus;
import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.IllustrationRepo;
import com.gaotianchi.resource.persistence.repo.SeriesRepo;
import com.gaotianchi.resource.persistence.repo.TagRepo;
import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.ArticleInfo;
import com.gaotianchi.resource.web.response.info.IllustrationInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;
import com.gaotianchi.resource.web.response.info.TagInfo;
import com.gaotianchi.resource.web.service.utlis.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.utlis.founder.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collection;

@Service
public class ArticleService implements ArticleServiceInterface {
    private final ArticleRepo articleRepo;
    private final TagRepo tagRepo;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;
    private final IllustrationRepo illustrationRepo;
    private final SeriesRepo seriesRepo;
    private final PaginationConfig paginationConfig;

    @Autowired
    public ArticleService(ArticleRepo articleRepo, TagRepo tagRepo, EntityFounderService entityFounderService, EntityBelongService entityBelongService, IllustrationRepo illustrationRepo, SeriesRepo seriesRepo, PaginationConfig paginationConfig) {
        this.articleRepo = articleRepo;
        this.tagRepo = tagRepo;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
        this.illustrationRepo = illustrationRepo;
        this.seriesRepo = seriesRepo;
        this.paginationConfig = paginationConfig;
    }

    @Override
    public ArticleInfo newArticle(String username) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setUser(userEntity);
        articleEntity = articleRepo.save(articleEntity);
        articleEntity.setSlug("Post_" + articleEntity.getId());
        articleEntity.setStatus(ArticleStatus.DRAFT);
        articleEntity.setCreationDatetime(OffsetDateTime.now());
        articleEntity.setUpdateDatetime(OffsetDateTime.now());
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleInfo(articleEntity);
    }

    @Override
    public void deleteArticle(String username, Long id) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        Collection<TagEntity> tagList = articleEntity.getTagList();
        for (TagEntity tag : tagList) {
            tag.getArticleList().remove(articleEntity);
        }
        tagRepo.saveAll(tagList);
        Collection<IllustrationEntity> illustrationList = articleEntity.getIllustrationList();
        for (IllustrationEntity illustration : illustrationList) {
            illustration.getArticleList().remove(articleEntity);
        }
        illustrationRepo.saveAll(illustrationList);
        articleRepo.delete(articleEntity);
    }

    @Override
    public void updateStatus(String username, Long id, String statusName) throws Exception {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        ArticleStatus from = articleEntity.getStatus();
        ArticleStatus to = ArticleStatus.valueOf(statusName.toUpperCase());
        if (!changeStatusIsPermitted(from, to)) throw new Exception("Invalid target status.");
        articleEntity.setStatus(to);
        if (to.equals(ArticleStatus.PUBLISHED)) {
            articleEntity.setPublishDatetime(OffsetDateTime.now());
        }
        articleEntity.setUpdateDatetime(OffsetDateTime.now());
        articleRepo.save(articleEntity);
    }

    @Override
    public void updateContent(String username, Long id, String newTitle, String newBody) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        articleEntity.setTitle(newTitle);
        articleEntity.setBody(newBody);
        articleRepo.save(articleEntity);
    }

    @Override
    public void updateSummary(String username, Long id, String newSummary) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        articleEntity.setSummary(newSummary);
        articleRepo.save(articleEntity);
    }

    @Override
    public void updateSlug(String username, Long id, String newSlug) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        articleEntity.setSlug(newSlug);
        articleRepo.save(articleEntity);
    }

    @Override
    public SeriesInfo setSeries(String username, Long id, Long newSeriesId) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        SeriesEntity oldSeries = articleEntity.getSeries();
        if (oldSeries != null) {
            oldSeries.getArticleList().remove(articleEntity);
            oldSeries.decreaseArticleCount();
            seriesRepo.save(oldSeries);
        }
        SeriesEntity newSeries = entityBelongService.seriesBelongToUser(username, newSeriesId);
        articleEntity.setSeries(newSeries);
        newSeries.getArticleList().add(articleEntity);
        newSeries.increaseArticleCount();
        seriesRepo.save(newSeries);

        articleRepo.save(articleEntity);
        return new SeriesInfo(newSeries);
    }

    @Override
    public void removeSeries(String username, Long id) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        SeriesEntity oldSeries = articleEntity.getSeries();
        if (oldSeries != null) {
            oldSeries.getArticleList().remove(articleEntity);
            oldSeries.decreaseArticleCount();
            articleEntity.setSeries(null);
            articleRepo.save(articleEntity);
        }
    }

    @Override
    public TagInfo addTag(String username, Long id, Long tagId) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
        articleEntity.getTagList().add(tagEntity);
        tagEntity.getArticleList().add(articleEntity);
        tagEntity.increaseArticleCount();
        articleRepo.save(articleEntity);
        return new TagInfo(tagEntity);
    }

    @Override
    public void removeTag(String username, Long id, Long tagId) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
        articleEntity.getTagList().remove(tagEntity);
        tagEntity.getArticleList().remove(articleEntity);
        tagEntity.decreaseArticleCount();
        articleRepo.save(articleEntity);
    }

    @Override
    public IllustrationInfo addIllustration(String username, Long id, Long illustrationId) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        IllustrationEntity illustrationEntity = entityBelongService.illustrationBelongToUser(username, illustrationId);
        articleEntity.getIllustrationList().add(illustrationEntity);
        illustrationEntity.getArticleList().add(articleEntity);
        articleRepo.save(articleEntity);
        return new IllustrationInfo(illustrationEntity);
    }

    @Override
    public void removeIllustration(String username, Long id, Long illustrationId) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        IllustrationEntity illustrationEntity = entityBelongService.illustrationBelongToUser(username, illustrationId);
        articleEntity.getIllustrationList().remove(illustrationEntity);
        illustrationEntity.getArticleList().remove(articleEntity);
        articleRepo.save(articleEntity);
    }

    @Override
    public ArticleInfo getInfo(Long id) {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(id);
        return new ArticleInfo(articleEntity);
    }

    @Override
    public String getBody(Long id) {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(id);
        return articleEntity.getBody();
    }

    @Override
    public PageInfo<ArticleInfo> getUserArticleInfoPage(Long userId, int page) {
        UserEntity userEntity = entityFounderService.getUserOrNorFound(userId);
        Pageable pageable = PageRequest.of(page, paginationConfig.getNumberOfInfoPerPage().getArticleForUser());
        Page<ArticleEntity> articleEntityPage = articleRepo.findByUserOrderByCreationDatetimeDesc(userEntity, pageable);
        return _getArticleInfoPage(articleEntityPage, page);
    }

    @Override
    public PageInfo<ArticleInfo> getSeriesArticleInfoPage(Long seriesId, int page) {
        SeriesEntity seriesEntity = entityFounderService.getSeriesOrNotFound(seriesId);
        Pageable pageable = PageRequest.of(page, paginationConfig.getNumberOfInfoPerPage().getArticleForSeries());
        Page<ArticleEntity> articleEntityPage = articleRepo.findBySeriesOrderByCreationDatetimeDesc(seriesEntity, pageable);
        return _getArticleInfoPage(articleEntityPage, page);
    }

    @Override
    public PageInfo<ArticleInfo> getTagArticleInfoPage(Long tagId, int page) {
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
        Pageable pageable = PageRequest.of(page, paginationConfig.getNumberOfInfoPerPage().getArticleForTag());
        Page<ArticleEntity> articleEntityPage = articleRepo.findByTagListContainingOrderByCreationDatetimeDesc(tagEntity, pageable);
        return _getArticleInfoPage(articleEntityPage, page);
    }

    @Override
    public PageInfo<ArticleInfo> getIllustrationArticleInfoPage(Long illustrationId, int page) {
        IllustrationEntity illustrationEntity = entityFounderService.getIllustrationOrNotFound(illustrationId);
        Pageable pageable = PageRequest.of(page, paginationConfig.getNumberOfInfoPerPage().getArticleForIllustration());
        Page<ArticleEntity> articleEntityPage = articleRepo.findByIllustrationListContainingOrderByCreationDatetimeDesc(illustrationEntity, pageable);
        return _getArticleInfoPage(articleEntityPage, page);
    }

    @Override
    public PageInfo<ArticleInfo> getCommentArticleInfoPage(Long commentId, int page) {
        return null;
    }

    private PageInfo<ArticleInfo> _getArticleInfoPage(Page<ArticleEntity> articleEntityPage, int page) {
        return new PageInfo<>(articleEntityPage.getContent().stream().map(ArticleInfo::new).toList(), articleEntityPage.getTotalPages(), page);
    }

    private boolean changeStatusIsPermitted(ArticleStatus from, ArticleStatus to) {
        return !from.equals(ArticleStatus.TRASH) || !to.equals(ArticleStatus.PUBLISHED);
    }
}
