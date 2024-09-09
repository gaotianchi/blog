package com.gaotianchi.resourceservice.web.service;

import com.gaotianchi.resourceservice.persistence.entity.*;
import com.gaotianchi.resourceservice.web.error.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.Function;

@Service
public class EntityBelongService {
    private final EntityFounderService entityFounderService;

    @Autowired
    public EntityBelongService(EntityFounderService entityFounderService) {
        this.entityFounderService = entityFounderService;
    }

    public ArticleEntity articleBelongToUser(String email, Long articleId) throws EntityNotFoundException {
        return belongsToUser(
                email,
                articleId,
                entityFounderService::getArticleOrNotFound,
                UserEntity::getArticleEntities,
                "Article " + articleId
        );
    }

    public ArticleEntity articleBelongToUser(UserEntity userEntity, Long articleId) throws EntityNotFoundException {
        return belongsToUser(
                userEntity,
                articleId,
                entityFounderService::getArticleOrNotFound,
                UserEntity::getArticleEntities,
                "Article " + articleId
        );
    }

    public ImageEntity imageBelongToUser(String email, Long coverId) throws EntityNotFoundException {
        return belongsToUser(
                email,
                coverId,
                entityFounderService::getImageOrNotFound,
                UserEntity::getImageEntities,
                "Image " + coverId
        );
    }

    public ImageEntity imageBelongToUser(UserEntity userEntity, Long coverId) throws EntityNotFoundException {
        return belongsToUser(
                userEntity,
                coverId,
                entityFounderService::getImageOrNotFound,
                UserEntity::getImageEntities,
                "Image " + coverId
        );
    }

    public CommentEntity commentBelongToUser(String email, Long commentId) throws EntityNotFoundException {
        return belongsToUser(
                email,
                commentId,
                entityFounderService::getCommentOrNotFound,
                UserEntity::getCommentEntities,
                "Comment " + commentId
        );
    }

    public SeriesEntity seriesBelongToUser(String email, Long seriesId) throws EntityNotFoundException {
        return belongsToUser(
                email,
                seriesId,
                entityFounderService::getSeriesOrNotFound,
                UserEntity::getSeriesEntities,
                "Series " + seriesId
        );
    }

    public <T> T belongsToUser(
            String email,
            Long entityId,
            Function<Long, T> entityFinder,
            Function<UserEntity, Collection<T>> userEntitiesGetter,
            String entityName
    ) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        return belongsToUser(userEntity, entityId, entityFinder, userEntitiesGetter, entityName);
    }

    public <T> T belongsToUser(
            UserEntity userEntity,
            Long entityId,
            Function<Long, T> entityFinder,
            Function<UserEntity, Collection<T>> userEntitiesGetter,
            String entityName
    ) throws EntityNotFoundException {
        T entity = entityFinder.apply(entityId);
        Collection<T> userEntities = userEntitiesGetter.apply(userEntity);
        if (!userEntities.contains(entity)) {
            throw new EntityNotFoundException(entityName + " " + entityId);
        }
        return entity;
    }
}
