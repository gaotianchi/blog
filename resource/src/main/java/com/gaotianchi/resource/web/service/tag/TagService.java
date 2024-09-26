package com.gaotianchi.resource.web.service.tag;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.TagEntity;
import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.TagRepo;
import com.gaotianchi.resource.web.error.EntityAlreadyExistException;
import com.gaotianchi.resource.web.response.info.TagInfo;
import com.gaotianchi.resource.web.response.page.PageTagInfo;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
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

    @Autowired
    public TagService(ArticleRepo articleRepo, TagRepo tagRepo, EntityFounderService entityFounderService) {
        this.articleRepo = articleRepo;
        this.tagRepo = tagRepo;
        this.entityFounderService = entityFounderService;
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
    public PageTagInfo getPageInfo(Integer page) {
        Pageable pageable = PageRequest.of(page, 100);
        Page<TagEntity> tagEntityPage = tagRepo.findByOrderByArticleCountDesc(pageable);
        List<TagInfo> tagInfoList = tagEntityPage.getContent().stream().map(TagInfo::new).toList();
        return new PageTagInfo(tagInfoList, tagEntityPage.getTotalPages(), page);
    }

    @Override
    public List<String> getAllNames() {
        return tagRepo.findAllTagNames();
    }
}
