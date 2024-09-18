package com.gaotianchi.resource.web.service.seriesservice;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.ImageEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.ImageRepo;
import com.gaotianchi.resource.persistence.repo.SeriesRepo;
import com.gaotianchi.resource.web.error.EntityNotFoundException;
import com.gaotianchi.resource.web.response.ArticleResponse;
import com.gaotianchi.resource.web.response.SeriesResponse;
import com.gaotianchi.resource.web.service.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService implements SeriesServiceInterface {
    private final SeriesRepo seriesRepo;
    private final ArticleRepo articleRepo;
    private final EntityFounderService entityFounderService;
    private final ImageRepo imageRepo;

    @Autowired
    public SeriesService(SeriesRepo seriesRepo, ArticleRepo articleRepo, EntityFounderService entityFounderService, ImageRepo imageRepo) {
        this.seriesRepo = seriesRepo;
        this.articleRepo = articleRepo;
        this.entityFounderService = entityFounderService;
        this.imageRepo = imageRepo;
    }

    @Override
    public SeriesResponse newSeries(String email, String name, Long coverId) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        ImageEntity imageEntity = entityFounderService.getImageOrNotFound(coverId);
        imageEntity.setForSeries(true);
        SeriesEntity seriesEntity = new SeriesEntity();
        seriesEntity.setUser(userEntity);
        seriesEntity.setCreationDatetime(OffsetDateTime.now());
        seriesEntity.setName(name);
        seriesEntity.setCover(imageRepo.save(imageEntity));
        seriesEntity = seriesRepo.save(seriesEntity);
        return new SeriesResponse(seriesEntity, true);
    }

    @Override
    public List<SeriesResponse> listSeries(String email) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        return userEntity.getSeriesEntities().stream()
                .map(seriesEntity -> new SeriesResponse(seriesEntity, true))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleResponse> listArticles(String email, Long seriesId) throws EntityNotFoundException {
        SeriesEntity seriesEntity = entityFounderService.getSeriesOrNotFound(seriesId);
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        if (!userEntity.getSeriesEntities().contains(seriesEntity))
            throw new EntityNotFoundException("Series " + seriesId);
        return seriesEntity.getArticleEntities().stream().map(ArticleResponse::new).collect(Collectors.toList());
    }

    @Override
    public void deleteSeries(String email, Long seriesId) throws EntityNotFoundException {
        SeriesEntity seriesEntity = entityFounderService.getSeriesOrNotFound(seriesId);
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        if (!userEntity.getSeriesEntities().contains(seriesEntity))
            throw new EntityNotFoundException("Series " + seriesId);
        Collection<ArticleEntity> articleEntities = seriesEntity.getArticleEntities();
        for (ArticleEntity articleEntity : articleEntities) {
            articleEntity.setSeriesEntity(null);
        }
        articleRepo.saveAll(articleEntities);
        seriesRepo.delete(seriesEntity);
    }
}
