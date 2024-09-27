package com.gaotianchi.resource.web.service.Illustration;

import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.IllustrationEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.IllustrationRepo;
import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.ArticleInfo;
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
import java.util.List;

@Service
public class IllustrationService implements IllustrationServiceInterface {

    private final IllustrationRepo illustrationRepo;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;
    private final ArticleRepo articleRepo;
    private final IllustrationStorageService illustrationStorageService;

    @Autowired
    public IllustrationService(IllustrationRepo illustrationRepo, EntityFounderService entityFounderService, EntityBelongService entityBelongService, ArticleRepo articleRepo, IllustrationStorageService illustrationStorageService) {
        this.illustrationRepo = illustrationRepo;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
        this.articleRepo = articleRepo;
        this.illustrationStorageService = illustrationStorageService;
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
        // TODO: 设置链接
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
    public PageInfo<IllustrationInfo> getPageInfo(String username, Integer page, boolean orderByCreationDatetime, boolean desc) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        Pageable pageable = PageRequest.of(page, 10);
        Page<IllustrationEntity> illustrationEntityPage;
        if (orderByCreationDatetime) {
            if (desc) {
                illustrationEntityPage = illustrationRepo.findByUserOrderByCreationDatetimeDesc(userEntity, pageable);
            } else {
                illustrationEntityPage = illustrationRepo.findByUserOrderByCreationDatetimeAsc(userEntity, pageable);
            }
        } else {
            if (desc) {
                illustrationEntityPage = illustrationRepo.findByUserOrderByUpdateDatetimeDesc(userEntity, pageable);
            } else {
                illustrationEntityPage = illustrationRepo.findByUserOrderByUpdateDatetimeAsc(userEntity, pageable);
            }
        }
        List<IllustrationInfo> illustrationInfoList = illustrationEntityPage.getContent().stream().map(IllustrationInfo::new).toList();
        return new PageInfo<>(illustrationInfoList, illustrationEntityPage.getTotalPages(), page);
    }

    @Override
    public List<ArticleInfo> getArticleList(String username, Long id) {
        IllustrationEntity illustrationEntity = entityBelongService.illustrationBelongToUser(username, id);
        return illustrationEntity.getArticleList().stream().map(ArticleInfo::new).toList();
    }
}
