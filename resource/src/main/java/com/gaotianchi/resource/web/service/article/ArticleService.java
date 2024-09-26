package com.gaotianchi.resource.web.service.article;

import com.gaotianchi.resource.persistence.entity.*;
import com.gaotianchi.resource.persistence.enums.ArticleStatus;
import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.IllustrationRepo;
import com.gaotianchi.resource.persistence.repo.TagRepo;
import com.gaotianchi.resource.web.response.info.ArticleInfo;
import com.gaotianchi.resource.web.response.info.IllustrationInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;
import com.gaotianchi.resource.web.response.info.TagInfo;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class ArticleService implements ArticleServiceInterface {
    private final ArticleRepo articleRepo;
    private final TagRepo tagRepo;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;
    private final IllustrationRepo illustrationRepo;

    @Autowired
    public ArticleService(ArticleRepo articleRepo, TagRepo tagRepo, EntityFounderService entityFounderService, EntityBelongService entityBelongService, IllustrationRepo illustrationRepo) {
        this.articleRepo = articleRepo;
        this.tagRepo = tagRepo;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
        this.illustrationRepo = illustrationRepo;
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
    public ArticleInfo getInfo(String username, Long id) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        return new ArticleInfo(articleEntity);
    }

    @Override
    public void updateStatus(String username, Long id, String newStatus) throws Exception {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        ArticleStatus from = articleEntity.getStatus();
        ArticleStatus to = ArticleStatus.valueOf(newStatus);
        if (!changeStatusIsPermitted(from, to)) throw new Exception("Invalid target status.");
        articleEntity.setStatus(to);
        if (to.equals(ArticleStatus.PUBLISHED)) {
            articleEntity.setPublishDatetime(OffsetDateTime.now());
        }
        articleEntity.setUpdateDatetime(OffsetDateTime.now());
        articleRepo.save(articleEntity);
    }

    @Override
    public void updateTitle(String username, Long id, String newTitle) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        articleEntity.setTitle(newTitle);
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
    public void updateBody(String username, Long id, String newBody) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        articleEntity.setBody(newBody);
        articleRepo.save(articleEntity);
    }

    @Override
    public String getBody(String username, Long id) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        return articleEntity.getBody();
    }

    @Override
    public SeriesInfo setSeries(String username, Long id, Long seriesId) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, seriesId);
        articleEntity.setSeries(seriesEntity);
        articleRepo.save(articleEntity);
        return new SeriesInfo(seriesEntity);
    }

    @Override
    public void removeSeries(String username, Long id) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        articleEntity.setSeries(null);
        articleRepo.save(articleEntity);
    }

    @Override
    public TagInfo addTag(String username, Long id, Long tagId) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
        articleEntity.getTagList().add(tagEntity);
        tagEntity.getArticleList().add(articleEntity);
        tagEntity.increaseArticleCount();
        tagRepo.save(tagEntity);
        articleRepo.save(articleEntity);
        return new TagInfo(tagEntity);
    }

    @Override
    public void removeTag(String username, Long tagId, Long id) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
        articleEntity.getTagList().remove(tagEntity);
        tagEntity.getArticleList().remove(articleEntity);
        tagEntity.decreaseArticleCount();
        tagRepo.save(tagEntity);
        articleRepo.save(articleEntity);
    }

    @Override
    public List<TagInfo> getTagList(String username, Long id) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        return articleEntity.getTagList().stream().map(TagInfo::new).toList();
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

    private boolean changeStatusIsPermitted(ArticleStatus from, ArticleStatus to) {
        return !from.equals(ArticleStatus.TRASH) || !to.equals(ArticleStatus.PUBLISHED);
    }
}
