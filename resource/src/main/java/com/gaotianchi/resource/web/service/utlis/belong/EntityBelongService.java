package com.gaotianchi.resource.web.service.utlis.belong;

import com.gaotianchi.resource.persistence.entity.*;
import com.gaotianchi.resource.web.error.EntityNotFoundException;
import com.gaotianchi.resource.web.service.utlis.founder.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.function.Function;

@Service
public class EntityBelongService implements EntityBelongServiceInterface {
    private final EntityFounderService entityFounderService;

    @Autowired
    public EntityBelongService(EntityFounderService entityFounderService) {
        this.entityFounderService = entityFounderService;
    }

    @Override
    public ArticleEntity articleBelongToUser(String username, Long id) throws EntityNotFoundException {
        return belongsToUser(
                username,
                id,
                entityFounderService::getArticleOrNotFound,
                UserEntity::getArticleList,
                "Article " + id
        );
    }

    @Override
    public SeriesEntity seriesBelongToUser(String username, Long id) throws EntityNotFoundException {
        return belongsToUser(
                username,
                id,
                entityFounderService::getSeriesOrNotFound,
                UserEntity::getSeriesList,
                "Series " + id
        );
    }

    @Override
    public SeriesCoverEntity seriesCoverBelongToUser(String username, Long id) throws EntityNotFoundException {
        return belongsToUser(
                username,
                id,
                entityFounderService::getSeriesCoverOrNotFound,
                UserEntity::getSeriesCoverList,
                "Series cover " + id
        );
    }

    @Override
    public IllustrationEntity illustrationBelongToUser(String username, Long id) throws EntityNotFoundException {
        return belongsToUser(
                username,
                id,
                entityFounderService::getIllustrationOrNotFound,
                UserEntity::getIllustrationList,
                "Illustration " + id
        );
    }

    public <T> T belongsToUser(
            String username,
            Long entityId,
            Function<Long, T> entityFinder,
            Function<UserEntity, Collection<T>> userEntitiesGetter,
            String entityName
    ) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
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
