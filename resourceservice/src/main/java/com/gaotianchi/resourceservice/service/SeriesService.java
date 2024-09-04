package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.error.ImageNotFoundException;
import com.gaotianchi.resourceservice.error.SeriesNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.ArticleEntity;
import com.gaotianchi.resourceservice.persistence.entity.ImageEntity;
import com.gaotianchi.resourceservice.persistence.entity.SeriesEntity;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import com.gaotianchi.resourceservice.persistence.repo.ArticleRepo;
import com.gaotianchi.resourceservice.persistence.repo.ImageRepo;
import com.gaotianchi.resourceservice.persistence.repo.SeriesRepo;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.SeriesOtd;
import com.gaotianchi.resourceservice.web.response.SeriesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesService {
    private final ImageRepo imageRepo;
    private final SeriesRepo seriesRepo;
    private final ArticleRepo articleRepo;
    private final EntityFounderService entityFounderService;

    @Autowired
    public SeriesService(ImageRepo imageRepo, SeriesRepo seriesRepo, ArticleRepo articleRepo, EntityFounderService entityFounderService) {
        this.imageRepo = imageRepo;
        this.seriesRepo = seriesRepo;
        this.articleRepo = articleRepo;
        this.entityFounderService = entityFounderService;
    }

    public SeriesResponse newSeries(String email, String name, Long coverId) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        ImageEntity imageEntity = entityFounderService.getImageOrNotFound(coverId);
        SeriesEntity seriesEntity = new SeriesEntity();
        seriesEntity.setUser(userEntity);
        seriesEntity.setCreationDatetime(OffsetDateTime.now());
        seriesEntity.setName(name);
        seriesEntity.setCover(imageEntity);
        seriesEntity = seriesRepo.save(seriesEntity);
        return new SeriesResponse(seriesEntity, true);
    }

    public List<SeriesResponse> listSeries(String email) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        return userEntity.getSeriesEntities().stream()
                .map(seriesEntity -> new SeriesResponse(seriesEntity, true))
                .collect(Collectors.toList());
    }

    public List<ArticleResponse> listArticles(String email, Long seriesId) throws EntityNotFoundException {
        SeriesEntity seriesEntity = entityFounderService.getSeriesOrNotFound(seriesId);
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        if (!userEntity.getSeriesEntities().contains(seriesEntity))
            throw new EntityNotFoundException("Series " + seriesId);
        return seriesEntity.getArticleEntities().stream().map(ArticleResponse::new).collect(Collectors.toList());
    }

    public void deleteSeries(Long id) throws EntityNotFoundException {
        SeriesEntity seriesEntity = entityFounderService.getSeriesOrNotFound(id);
        Collection<ArticleEntity> articleEntities = seriesEntity.getArticleEntities();
        for (ArticleEntity articleEntity : articleEntities) {
            articleEntity.setSeriesEntity(null);
        }
        articleRepo.saveAll(articleEntities);
        seriesRepo.delete(seriesEntity);
    }

    public ImageEntity getArticleImageOrNotFound(Long id) throws ImageNotFoundException {
        Optional<ImageEntity> articleImageEntity = imageRepo.findById(id);
        if (articleImageEntity.isEmpty()) throw new ImageNotFoundException();
        return articleImageEntity.get();
    }

    public SeriesEntity getSeriesEntityOrNotFound(Long id) throws SeriesNotFoundException {
        Optional<SeriesEntity> seriesEntity = seriesRepo.findById(id);
        if (seriesEntity.isEmpty()) throw new SeriesNotFoundException();
        return seriesEntity.get();
    }

    public SeriesEntity updateSeriesInfo(Long id, String name) throws SeriesNotFoundException {
        SeriesEntity seriesEntity = getSeriesEntityOrNotFound(id);
        seriesEntity.setName(name);
        return seriesRepo.save(seriesEntity);
    }
    public SeriesEntity updateSeriesCover(Long id, Long coverId) throws SeriesNotFoundException, ImageNotFoundException {
        SeriesEntity seriesEntity = getSeriesEntityOrNotFound(id);
        ImageEntity imageEntity = getArticleImageOrNotFound(coverId);
        seriesEntity.setCover(imageEntity);
        return seriesRepo.save(seriesEntity);
    }

    public SeriesEntity getSeriesInfo(Long id) throws SeriesNotFoundException {
        return getSeriesEntityOrNotFound(id);
    }


    public List<SeriesOtd> getAllSeries() {
        List<SeriesOtd> seriesOtds = new ArrayList<>();
        for (SeriesEntity seriesEntity : seriesRepo.findAll()) {
            seriesOtds.add(new SeriesOtd(seriesEntity));
        }
        return seriesOtds;
    }
}
