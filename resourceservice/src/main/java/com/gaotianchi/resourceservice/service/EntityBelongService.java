package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.ArticleEntity;
import com.gaotianchi.resourceservice.persistence.entity.SeriesEntity;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityBelongService {
    private final EntityFounderService entityFounderService;

    @Autowired
    public EntityBelongService(EntityFounderService entityFounderService) {
        this.entityFounderService = entityFounderService;
    }

    public ArticleEntity articleBelongToUser(String email, Long articleId) throws EntityNotFoundException {
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        if (!userEntity.getArticleEntities().contains(articleEntity))
            throw new EntityNotFoundException("Article " + articleId);
        return articleEntity;
    }

    public SeriesEntity seriesBelongToUser(String email, Long seriesId) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        SeriesEntity seriesEntity = entityFounderService.getSeriesOrNotFound(seriesId);
        if (!userEntity.getSeriesEntities().contains(seriesEntity))
            throw new EntityNotFoundException("Series " + seriesId);
        return seriesEntity;
    }
}
