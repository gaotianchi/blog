package com.gaotianchi.resource.web.service.belong;

import com.gaotianchi.resource.persistence.entity.*;
import com.gaotianchi.resource.web.error.EntityNotFoundException;

public interface EntityBelongServiceInterface {
    ArticleEntity articleBelongToUser(String username, Long id) throws EntityNotFoundException;

    SeriesEntity seriesBelongToUser(String username, Long id) throws EntityNotFoundException;

    AvatarEntity avatarBelongToUser(String username, Long id) throws EntityNotFoundException;

    SeriesCoverEntity seriesCoverBelongToUser(String username, Long id) throws EntityNotFoundException;

    IllustrationEntity illustrationBelongToUser(String username, Long id) throws EntityNotFoundException;
}
