package com.gaotianchi.resource.web.service.tag;

import com.gaotianchi.resource.config.PaginationConfig;
import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.TagEntity;
import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.TagRepo;
import com.gaotianchi.resource.web.error.EntityAlreadyExistException;
import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.TagInfo;
import com.gaotianchi.resource.web.service.utlis.founder.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class TagService implements TagServiceInterface {
    private final ArticleRepo articleRepo;
    private final TagRepo tagRepo;
    private final EntityFounderService entityFounderService;
    private final PaginationConfig paginationConfig;

    @Autowired
    public TagService(ArticleRepo articleRepo, TagRepo tagRepo, EntityFounderService entityFounderService, PaginationConfig paginationConfig) {
        this.articleRepo = articleRepo;
        this.tagRepo = tagRepo;
        this.entityFounderService = entityFounderService;
        this.paginationConfig = paginationConfig;
    }

    @Override
    public TagInfo newTag(String name) {
        TagEntity tagEntity = tagRepo.findByName(name);
        if (tagEntity != null) throw new EntityAlreadyExistException("Tag " + name);
        tagEntity = new TagEntity();
        tagEntity.setCreationDatetime(OffsetDateTime.now());
        tagEntity.setName(name);
        return new TagInfo(tagRepo.save(tagEntity));
    }

    @Override
    public void deleteTag(Long id) {
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(id);
        Collection<ArticleEntity> articles = tagEntity.getArticleList();
        for (ArticleEntity article : articles) {
            article.getTagList().remove(tagEntity);
        }
        articleRepo.saveAll(articles);
        tagEntity.getArticleList().clear();
        tagRepo.delete(tagEntity);
    }

    @Override
    public TagInfo getInfo(Long id) {
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(id);
        return new TagInfo(tagEntity);
    }

    @Override
    public PageInfo<TagInfo> getPageInfo(Integer page) {
        Pageable pageable = PageRequest.of(page, paginationConfig.getNumberOfInfoPerPage().getTag());
        Page<TagEntity> tagEntityPage = tagRepo.findByOrderByArticleCountDesc(pageable);
        List<TagInfo> tagInfoList = tagEntityPage.getContent().stream().map(TagInfo::new).toList();
        return new PageInfo<>(tagInfoList, tagEntityPage.getTotalPages(), page);
    }

    @Override
    public List<String> getAllNames() {
        return tagRepo.findAllTagNames();
    }

    @Override
    public List<TagInfo> getArticleTagList(Long articleId) {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
        return articleEntity.getTagList().stream().map(TagInfo::new).toList();
    }
}
