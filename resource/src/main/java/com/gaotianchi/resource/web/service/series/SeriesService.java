package com.gaotianchi.resource.web.service.series;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.SeriesCoverEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.SeriesCoverRepo;
import com.gaotianchi.resource.persistence.repo.SeriesRepo;
import com.gaotianchi.resource.web.response.info.SeriesCoverInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;
import com.gaotianchi.resource.web.response.page.PageSeriesInfo;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import com.gaotianchi.resource.web.service.storage.cover.SeriesCoverStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class SeriesService implements SeriesServiceInterface {
    private final SeriesRepo seriesRepo;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;
    private final SeriesCoverRepo seriesCoverRepo;
    private final SeriesCoverStorageService seriesCoverStorageService;
    private final ArticleRepo articleRepo;

    @Autowired
    public SeriesService(SeriesRepo seriesRepo, EntityFounderService entityFounderService, EntityBelongService entityBelongService, SeriesCoverRepo seriesCoverRepo, SeriesCoverStorageService seriesCoverStorageService, ArticleRepo articleRepo) {
        this.seriesRepo = seriesRepo;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
        this.seriesCoverRepo = seriesCoverRepo;
        this.seriesCoverStorageService = seriesCoverStorageService;
        this.articleRepo = articleRepo;
    }

    @Override
    public SeriesInfo newSeries(String username, String title, String profile) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        SeriesEntity seriesEntity = new SeriesEntity();
        seriesEntity.setUser(userEntity);
        seriesEntity.setCreationDatetime(OffsetDateTime.now());
        seriesEntity.setTitle(title);
        if (profile != null && !profile.isEmpty()) {
            seriesEntity.setProfile(profile);
        }
        return new SeriesInfo(seriesRepo.save(seriesEntity));
    }

    @Override
    public void updateContent(String username, Long id, String newTitle, String nProfile) {
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, id);
        seriesEntity.setTitle(newTitle);
        if (nProfile != null && !nProfile.isEmpty()) {
            seriesEntity.setProfile(nProfile);
        }
        seriesRepo.save(seriesEntity);
    }

    @Override
    public void deleteSeries(String username, Long id) throws IOException {
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, id);
        Collection<ArticleEntity> articleEntities = seriesEntity.getArticleList();
        for (ArticleEntity articleEntity : articleEntities) {
            articleEntity.setSeries(null);
        }
        articleRepo.saveAll(articleEntities);
        SeriesCoverEntity cover = seriesEntity.getCover();
        if (cover != null) {
            seriesCoverStorageService.delete(cover.getFilename());
            seriesCoverRepo.delete(cover);
        }
        seriesRepo.delete(seriesEntity);
    }

    @Override
    public SeriesInfo getInfo(Long id) {
        SeriesEntity seriesEntity = entityFounderService.getSeriesOrNotFound(id);
        return new SeriesInfo(seriesEntity);
    }

    @Override
    public PageSeriesInfo getPageInfo(String username, Integer page) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        Pageable pageable = PageRequest.of(page, 10);
        Page<SeriesEntity> seriesEntityPage = seriesRepo.findByUserOrderByCreationDatetimeDesc(userEntity, pageable);
        List<SeriesInfo> seriesEntityList = seriesEntityPage.getContent().stream().map(SeriesInfo::new).toList();
        return new PageSeriesInfo(seriesEntityList, seriesEntityPage.getTotalPages(), page);
    }

    @Override
    public SeriesCoverInfo setCover(String username, Long id, Long newCoverId) {
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, id);
        SeriesCoverEntity seriesCoverEntity = entityBelongService.seriesCoverBelongToUser(username, newCoverId);
        seriesEntity.setCover(seriesCoverEntity);
        seriesRepo.save(seriesEntity);
        return new SeriesCoverInfo(seriesCoverEntity);
    }

    @Override
    public SeriesCoverInfo updateCover(String username, Long id, Long newCoverId) throws IOException {
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, id);
        SeriesCoverEntity newCover = entityBelongService.seriesCoverBelongToUser(username, newCoverId);
        SeriesCoverEntity oldCover = seriesEntity.getCover();
        seriesEntity.setCover(newCover);
        seriesRepo.save(seriesEntity);
        if (oldCover != null) {
            seriesCoverStorageService.delete(oldCover.getFilename());
            seriesCoverRepo.delete(oldCover);
        }
        return new SeriesCoverInfo(seriesEntity.getCover());
    }

    @Override
    public void removeCover(String username, Long id) throws IOException {
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, id);
        SeriesCoverEntity oldCover = seriesEntity.getCover();
        seriesEntity.setCover(null);
        seriesRepo.save(seriesEntity);
        if (oldCover != null) {
            seriesCoverStorageService.delete(oldCover.getFilename());
            seriesCoverRepo.delete(oldCover);
        }
    }
}
