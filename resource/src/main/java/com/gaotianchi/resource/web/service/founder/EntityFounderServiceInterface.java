package com.gaotianchi.resource.web.service.founder;

import com.gaotianchi.resource.persistence.entity.*;
import com.gaotianchi.resource.web.error.EntityNotFoundException;

public interface EntityFounderServiceInterface {
    UserEntity getUserOrNotFound(String username) throws EntityNotFoundException;

    ArticleEntity getArticleOrNotFound(Long id) throws EntityNotFoundException;

    IllustrationEntity getIllustrationOrNotFound(Long id) throws EntityNotFoundException;

    CommentEntity getCommentOrNotFound(Long id) throws EntityNotFoundException;

    SeriesEntity getSeriesOrNotFound(Long id) throws EntityNotFoundException;

    TagEntity getTagOrNotFound(Long id) throws EntityNotFoundException;

    AvatarEntity getAvatarOrNotFound(Long id) throws EntityNotFoundException;

    SeriesCoverEntity getSeriesCoverOrNotFound(Long id) throws EntityNotFoundException;
}
