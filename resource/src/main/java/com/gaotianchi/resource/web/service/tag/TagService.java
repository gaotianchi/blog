package com.gaotianchi.resource.web.service.tag;

import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.TagRepo;
import com.gaotianchi.resource.web.response.TagInfo;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public TagInfo newTag(String name) {
        return null;
    }

    @Override
    public void deleteTag(Long id) {

    }

//    public TagResponse newTag(String name) throws EntityAlreadyExistException {
//        TagEntity tagEntity = tagRepo.findByName(name);
//        if (tagEntity != null) throw new EntityAlreadyExistException("Tag " + name);
//        tagEntity = new TagEntity();
//        tagEntity.setCreationDatetime(OffsetDateTime.now());
//        tagEntity.setName(name);
//        tagEntity = tagRepo.save(tagEntity);
//        return new TagResponse(tagEntity);
//    }

//    public List<TagResponse> listTags() {
//        Collection<TagEntity> tagEntities = tagRepo.findAll();
//        return tagEntities.stream().map(TagResponse::new).collect(Collectors.toList());
//    }

//    @Override
//    public void deleteTag(Long id) throws EntityNotFoundException {
//        TagEntity tagEntity = entityFounderService.getTagOrNotFound(id);
//        Collection<ArticleEntity> articleEntities = tagEntity.getArticleList();
//        if (!articleEntities.isEmpty()) {
//            for (ArticleEntity articleEntity : articleEntities) {
//                articleEntity.getTagList().removeIf(t -> Objects.equals(t.getId(), id));
//            }
//            articleRepo.saveAll(articleEntities);
//        }
//        tagRepo.delete(tagEntity);
//    }
//    public List<ArticleResponse> listArticles(Long id) throws EntityNotFoundException {
//        TagEntity tagEntity = entityFounderService.getTagOrNotFound(id);
//        return tagEntity.getArticleList().stream().map(ArticleResponse::new).collect(Collectors.toList());
//    }
//
//    public TagResponse setArticle(String email, Long tagId, Long articleId) throws EntityNotFoundException {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(email, articleId);
//        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
//        if (!tagEntity.getArticleList().contains(articleEntity)) tagEntity.getArticleList().add(articleEntity);
//        tagEntity = tagRepo.save(tagEntity);
//        return new TagResponse(tagEntity);
//    }

}
