package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.entity.ArticleEntity;
import com.gaotianchi.resourceservice.entity.ArticleImageEntity;
import com.gaotianchi.resourceservice.entity.SeriesEntity;
import com.gaotianchi.resourceservice.repo.ArticleImageRepo;
import com.gaotianchi.resourceservice.repo.ArticleRepo;
import com.gaotianchi.resourceservice.repo.SeriesRepo;
import com.gaotianchi.resourceservice.web.error.ArticleImageNotFoundException;
import com.gaotianchi.resourceservice.web.error.SeriesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class SeriesService {
    private final ArticleImageRepo articleImageRepo;
    private final SeriesRepo seriesRepo;
    private final ArticleRepo articleRepo;

    @Autowired
    public SeriesService(ArticleImageRepo articleImageRepo, SeriesRepo seriesRepo, ArticleRepo articleRepo) {
        this.articleImageRepo = articleImageRepo;
        this.seriesRepo = seriesRepo;
        this.articleRepo = articleRepo;
    }

    public SeriesEntity newSeries(String name, Long coverId) throws ArticleImageNotFoundException {
        ArticleImageEntity articleImageEntity = getArticleImageOrNotFound(coverId);
        SeriesEntity seriesEntity = new SeriesEntity();
        seriesEntity.setCreationDatetime(OffsetDateTime.now());
        seriesEntity.setName(name);
        seriesEntity.setCover(articleImageEntity);
        return seriesRepo.save(seriesEntity);
    }

    public ArticleImageEntity getArticleImageOrNotFound(Long id) throws ArticleImageNotFoundException {
        Optional<ArticleImageEntity> articleImageEntity = articleImageRepo.findById(id);
        if (articleImageEntity.isEmpty()) throw new ArticleImageNotFoundException();
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
    public SeriesEntity updateSeriesCover(Long id, Long coverId) throws SeriesNotFoundException, ArticleImageNotFoundException {
        SeriesEntity seriesEntity = getSeriesEntityOrNotFound(id);
        ArticleImageEntity articleImageEntity = getArticleImageOrNotFound(coverId);
        seriesEntity.setCover(articleImageEntity);
        return seriesRepo.save(seriesEntity);
    }

    public SeriesEntity getSeriesInfo(Long id) throws SeriesNotFoundException {
        return getSeriesEntityOrNotFound(id);
    }


}
