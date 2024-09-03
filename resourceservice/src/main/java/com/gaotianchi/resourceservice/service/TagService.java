package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.error.ArticleNotFoundException;
import com.gaotianchi.resourceservice.error.TagAlreadyExistException;
import com.gaotianchi.resourceservice.error.TagNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.ArticleEntity;
import com.gaotianchi.resourceservice.persistence.entity.TagEntity;
import com.gaotianchi.resourceservice.persistence.repo.ArticleRepo;
import com.gaotianchi.resourceservice.persistence.repo.TagRepo;
import com.gaotianchi.resourceservice.web.response.ArticleOtd;
import com.gaotianchi.resourceservice.web.response.TagOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class TagService {
    private final ArticleRepo articleRepo;
    private final TagRepo tagRepo;
    private final ArticleService articleService;

    @Autowired
    public TagService(ArticleRepo articleRepo, TagRepo tagRepo, ArticleService articleService) {
        this.articleRepo = articleRepo;
        this.tagRepo = tagRepo;
        this.articleService = articleService;
    }

    public TagEntity createNewTag(String name, Long articleId) throws ArticleNotFoundException, TagAlreadyExistException {
        TagEntity tagEntity = tagRepo.findByName(name);
        if (tagEntity != null) throw new TagAlreadyExistException();
        tagEntity = new TagEntity();
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        Collection<ArticleEntity> articleEntities = tagEntity.getArticles();
        articleEntities.add(articleEntity);
        tagEntity.setArticles(articleEntities);
        tagEntity.setCreationDatetime(OffsetDateTime.now());
        tagEntity.setName(name);
        return tagRepo.save(tagEntity);
    }

    public ArticleEntity getArticleOrNotFound(Long articleId) throws ArticleNotFoundException {
        Optional<ArticleEntity> article = articleRepo.findById(articleId);
        if (article.isEmpty()) throw new ArticleNotFoundException();
        return article.get();
    }

    public void deleteTag(Long id) throws TagNotFoundException {
        TagEntity tagEntity = getTagOrNotFound(id);
        if (!tagEntity.getArticles().isEmpty()) {
            for (ArticleEntity articleEntity : tagEntity.getArticles()) {
                articleEntity.getTags().removeIf(t -> Objects.equals(t.getId(), id));
            }
            articleRepo.saveAll(tagEntity.getArticles());
        }
        tagRepo.delete(tagEntity);
    }

    public TagEntity getTagOrNotFound(Long id) throws TagNotFoundException {
        Optional<TagEntity> tagEntity = tagRepo.findById(id);
        if (tagEntity.isEmpty()) throw new TagNotFoundException();
        return tagEntity.get();
    }

    public List<TagOtd> getAllTags() {
        List<TagOtd> tagOtds = new ArrayList<>();
        for (TagEntity tagEntity : tagRepo.findAll()) {
            tagOtds.add(new TagOtd(tagEntity));
        }
        return tagOtds;
    }

    public TagEntity getTagOrNotFound(String name) throws TagNotFoundException {
        TagEntity tagEntity = tagRepo.findByName(name);
        if (tagEntity == null) throw new TagNotFoundException();
        return tagEntity;
    }

    public List<ArticleOtd> getAllArticlesFromTags(Long id) throws TagNotFoundException {
        TagEntity tagEntity = getTagOrNotFound(id);
        List<ArticleOtd> articleOtds = new ArrayList<>();
        for (ArticleEntity articleEntity : tagEntity.getArticles()) {
            ArticleOtd articleOtd = articleService.getArticleOtd(articleEntity);
            articleOtds.add(articleOtd);
        }
        return articleOtds;
    }
}
