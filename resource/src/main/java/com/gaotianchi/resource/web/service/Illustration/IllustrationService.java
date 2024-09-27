package com.gaotianchi.resource.web.service.Illustration;

import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.config.PaginationConfig;
import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.IllustrationEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.IllustrationRepo;
import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.IllustrationInfo;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import com.gaotianchi.resource.web.service.storage.illustration.IllustrationStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@Service
public class IllustrationService implements IllustrationServiceInterface {

    private final IllustrationRepo illustrationRepo;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;
    private final ArticleRepo articleRepo;
    private final IllustrationStorageService illustrationStorageService;
    private final PaginationConfig paginationConfig;

    @Autowired
    public IllustrationService(IllustrationRepo illustrationRepo, EntityFounderService entityFounderService, EntityBelongService entityBelongService, ArticleRepo articleRepo, IllustrationStorageService illustrationStorageService, PaginationConfig paginationConfig) {
        this.illustrationRepo = illustrationRepo;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
        this.articleRepo = articleRepo;
        this.illustrationStorageService = illustrationStorageService;
        this.paginationConfig = paginationConfig;
    }

    @Override
    public IllustrationInfo newIllustration(String username, MultipartFile file, String title, String alt) throws IOException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        String filename = Utils.generateUniqueFileName() + Utils.getFileExtension(file.getOriginalFilename());
        illustrationStorageService.save(filename, file);
        IllustrationEntity illustrationEntity = new IllustrationEntity();
        illustrationEntity.setFilename(filename);
        illustrationEntity.setUser(userEntity);
        illustrationEntity.setTitle(title);
        illustrationEntity.setUrl("http://localhost:8090/images/illustration/" + filename);
        if (alt != null && !alt.isEmpty()) {
            illustrationEntity.setAlt(alt);
        }
        return new IllustrationInfo(illustrationRepo.save(illustrationEntity));
    }

    @Override
    public void deleteIllustration(String username, Long id) throws IOException {
        IllustrationEntity illustrationEntity = entityBelongService.illustrationBelongToUser(username, id);
        illustrationStorageService.delete(illustrationEntity.getFilename());
        Collection<ArticleEntity> articleEntities = illustrationEntity.getArticleList();
        for (ArticleEntity articleEntity : articleEntities) {
            articleEntity.getIllustrationList().remove(illustrationEntity);
        }
        articleRepo.saveAll(articleEntities);
        illustrationEntity.getArticleList().clear();
        illustrationRepo.delete(illustrationEntity);
    }

    @Override
    public void updateContent(String username, Long id, String newTitle, String newAlt) {
        IllustrationEntity illustrationEntity = entityBelongService.illustrationBelongToUser(username, id);
        illustrationEntity.setTitle(newTitle);
        illustrationEntity.setAlt(newAlt);
        illustrationRepo.save(illustrationEntity);
    }

    @Override
    public IllustrationInfo getInfo(Long id) {
        IllustrationEntity illustrationEntity = entityFounderService.getIllustrationOrNotFound(id);
        return new IllustrationInfo(illustrationEntity);
    }

    @Override
    public PageInfo<IllustrationInfo> getUserIllustrationInfoPage(Long userId, int page) {
        UserEntity userEntity = entityFounderService.getUserOrNorFound(userId);
        Pageable pageable = PageRequest.of(page, paginationConfig.getNumberOfInfoPerPage().getIllustrationForUser());
        Page<IllustrationEntity> illustrationEntityPage = illustrationRepo.findByUserOrderByCreationDatetimeDesc(userEntity, pageable);
        return _getIllustrationPageInfo(illustrationEntityPage, page);
    }

    @Override
    public PageInfo<IllustrationInfo> getArticleIllustrationInfoPage(Long articleId, int page) {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
        Pageable pageable = PageRequest.of(page, paginationConfig.getNumberOfInfoPerPage().getIllustrationForArticle());
        Page<IllustrationEntity> illustrationEntityPage = illustrationRepo.findByArticleListContaining(articleEntity, pageable);
        return _getIllustrationPageInfo(illustrationEntityPage, page);
    }

    private PageInfo<IllustrationInfo> _getIllustrationPageInfo(Page<IllustrationEntity> illustrationEntityPage, int page) {
        return new PageInfo<>(illustrationEntityPage.getContent().stream().map(IllustrationInfo::new).toList(), illustrationEntityPage.getTotalPages(), page);
    }
}
