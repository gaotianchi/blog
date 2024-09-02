package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.entity.ArticleEntity;
import com.gaotianchi.resourceservice.entity.ImageEntity;
import com.gaotianchi.resourceservice.entity.SeriesEntity;
import com.gaotianchi.resourceservice.repo.ArticleRepo;
import com.gaotianchi.resourceservice.repo.ImageRepo;
import com.gaotianchi.resourceservice.repo.SeriesRepo;
import com.gaotianchi.resourceservice.web.error.ImageNotFoundException;
import com.gaotianchi.resourceservice.web.error.SeriesNotFoundException;
import com.gaotianchi.resourceservice.web.otd.SeriesOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {
    private final ImageRepo imageRepo;
    private final SeriesRepo seriesRepo;
    private final ArticleRepo articleRepo;

    @Autowired
    public SeriesService(ImageRepo imageRepo, SeriesRepo seriesRepo, ArticleRepo articleRepo) {
        this.imageRepo = imageRepo;
        this.seriesRepo = seriesRepo;
        this.articleRepo = articleRepo;
    }

    public SeriesEntity newSeries(String name, Long coverId) throws ImageNotFoundException {
        ImageEntity imageEntity = getArticleImageOrNotFound(coverId);
        SeriesEntity seriesEntity = new SeriesEntity();
        seriesEntity.setCreationDatetime(OffsetDateTime.now());
        seriesEntity.setName(name);
        seriesEntity.setCover(imageEntity);
        return seriesRepo.save(seriesEntity);
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

    public void deleteSeries(Long id) throws SeriesNotFoundException {
        SeriesEntity seriesEntity = getSeriesEntityOrNotFound(id);
        if (!seriesEntity.getArticleEntities().isEmpty()) {
            for (ArticleEntity articleEntity : seriesEntity.getArticleEntities()) {
                articleEntity.setSeriesEntity(null);
                articleRepo.save(articleEntity);
            }
        }
        seriesRepo.delete(seriesEntity);
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
