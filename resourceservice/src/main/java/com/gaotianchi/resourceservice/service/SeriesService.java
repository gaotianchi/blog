package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.entity.ArticleImageEntity;
import com.gaotianchi.resourceservice.entity.SeriesEntity;
import com.gaotianchi.resourceservice.repo.ArticleImageRepo;
import com.gaotianchi.resourceservice.repo.SeriesRepo;
import com.gaotianchi.resourceservice.web.error.ArticleImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class SeriesService {
    private final ArticleImageRepo articleImageRepo;
    private final SeriesRepo seriesRepo;

    @Autowired
    public SeriesService(ArticleImageRepo articleImageRepo, SeriesRepo seriesRepo) {
        this.articleImageRepo = articleImageRepo;
        this.seriesRepo = seriesRepo;
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
}
