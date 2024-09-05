package com.gaotianchi.resourceservice.service.tagservice;

import com.gaotianchi.resourceservice.persistence.entity.ArticleEntity;
import com.gaotianchi.resourceservice.persistence.entity.TagEntity;
import com.gaotianchi.resourceservice.persistence.repo.ArticleRepo;
import com.gaotianchi.resourceservice.persistence.repo.TagRepo;
import com.gaotianchi.resourceservice.service.EntityBelongService;
import com.gaotianchi.resourceservice.service.EntityFounderService;
import com.gaotianchi.resourceservice.web.error.EntityAlreadyExistException;
import com.gaotianchi.resourceservice.web.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.TagResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TagService implements TagServiceInterface {
    private final ArticleRepo articleRepo;
    private final TagRepo tagRepo;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;

    @Autowired
    public TagService(ArticleRepo articleRepo, TagRepo tagRepo, EntityFounderService entityFounderService, EntityBelongService entityBelongService) {
        this.articleRepo = articleRepo;
        this.tagRepo = tagRepo;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
    }

    @Override
    public TagResponse newTag(String name) throws EntityAlreadyExistException {
        TagEntity tagEntity = tagRepo.findByName(name);
        if (tagEntity != null) throw new EntityAlreadyExistException("Tag " + name);
        tagEntity = new TagEntity();
        tagEntity.setCreationDatetime(OffsetDateTime.now());
        tagEntity.setName(name);
        tagEntity = tagRepo.save(tagEntity);
        return new TagResponse(tagEntity);
    }

    @Override
    public List<TagResponse> listTags() {
        Collection<TagEntity> tagEntities = tagRepo.findAll();
        return tagEntities.stream().map(TagResponse::new).collect(Collectors.toList());
    }

    @Override
    public void deleteTag(Long id) throws EntityNotFoundException {
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(id);
        Collection<ArticleEntity> articleEntities = tagEntity.getArticles();
        if (!articleEntities.isEmpty()) {
            for (ArticleEntity articleEntity : articleEntities) {
                articleEntity.getTags().removeIf(t -> Objects.equals(t.getId(), id));
            }
            articleRepo.saveAll(articleEntities);
        }
        tagRepo.delete(tagEntity);
    }

    @Override
    public List<ArticleResponse> listArticles(Long id) throws EntityNotFoundException {
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(id);
        return tagEntity.getArticles().stream().map(ArticleResponse::new).collect(Collectors.toList());
    }

    @Override
    public TagResponse setArticle(String email, Long tagId, Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
        if (!tagEntity.getArticles().contains(articleEntity)) tagEntity.getArticles().add(articleEntity);
        tagEntity = tagRepo.save(tagEntity);
        return new TagResponse(tagEntity);
    }

}
