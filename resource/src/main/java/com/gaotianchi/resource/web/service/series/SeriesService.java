package com.gaotianchi.resource.web.service.series;

import com.gaotianchi.resource.persistence.entity.SeriesCoverEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.SeriesCoverRepo;
import com.gaotianchi.resource.persistence.repo.SeriesRepo;
import com.gaotianchi.resource.web.error.EntityNotFoundException;
import com.gaotianchi.resource.web.response.*;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService implements SeriesServiceInterface {
    private final SeriesRepo seriesRepo;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;
    private final SeriesCoverRepo seriesCoverRepo;

    @Autowired
    public SeriesService(SeriesRepo seriesRepo, EntityFounderService entityFounderService, EntityBelongService entityBelongService, SeriesCoverRepo seriesCoverRepo) {
        this.seriesRepo = seriesRepo;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
        this.seriesCoverRepo = seriesCoverRepo;
    }

    public List<SeriesResponse> listSeries(String email) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        return userEntity.getSeriesList().stream()
                .map(seriesEntity -> new SeriesResponse(seriesEntity, true))
                .collect(Collectors.toList());
    }

    public List<ArticleResponse> listArticles(String email, Long seriesId) throws EntityNotFoundException {
        SeriesEntity seriesEntity = entityFounderService.getSeriesOrNotFound(seriesId);
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        if (!userEntity.getSeriesList().contains(seriesEntity))
            throw new EntityNotFoundException("Series " + seriesId);
        return seriesEntity.getArticleList().stream().map(ArticleResponse::new).collect(Collectors.toList());
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
    public void deleteSeries(String username, Long id) {
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, id);
        // 删除系列实体
        // 更新该系列下所有文章的系列属性
        // 删除系列封面实体
        // 删除系列封面文件
    }

    @Override
    public SeriesInfo getInfo(String username, Long id) {
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, id);
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
    public SeriesCoverInfo setCover(String username, Long id, Long coverId) {
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, id);
        SeriesCoverEntity seriesCoverEntity = entityBelongService.seriesCoverBelongToUser(username, coverId);
        seriesEntity.setCover(seriesCoverEntity);
        seriesRepo.save(seriesEntity);
        return new SeriesCoverInfo(seriesCoverEntity);
    }

    @Override
    public SeriesCoverInfo updateCover(String username, Long id, Long oldCoverId, Long newCoverId) {
        // 设置新封面
        // 删除旧封面
        return null;
    }

    @Override
    public void removeCover(String username, Long id, Long coverId) {
        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, id);
        SeriesCoverEntity seriesCoverEntity = entityBelongService.seriesCoverBelongToUser(username, coverId);
        if (seriesEntity.getCover().equals(seriesCoverEntity)) {
            // 删除封面文件
            // 删除封面实体
            seriesCoverRepo.delete(seriesCoverEntity);
        }
    }

    @Override
    public ArticleInfo addArticle(String username, Long id, Long articleId) {
        return null;
    }

    @Override
    public void removeArticle(String username, Long id, Long articleId) {

    }
}
