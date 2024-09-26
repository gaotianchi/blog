package com.gaotianchi.resource.web.service.founder;

import com.gaotianchi.resource.persistence.entity.*;
import com.gaotianchi.resource.persistence.repo.*;
import com.gaotianchi.resource.web.error.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntityFounderService implements EntityFounderServiceInterface {
    private final UserRepo userRepo;
    private final IllustrationRepo illustrationRepo;
    private final ArticleRepo articleRepo;
    private final SeriesRepo seriesRepo;
    private final TagRepo tagRepo;
    private final CommentRepo commentRepo;
    private final AvatarRepo avatarRepo;
    private final SeriesCoverRepo seriesCoverRepo;

    @Autowired
    public EntityFounderService(UserRepo userRepo, IllustrationRepo illustrationRepo, ArticleRepo articleRepo, SeriesRepo seriesRepo, TagRepo tagRepo, CommentRepo commentRepo, AvatarRepo avatarRepo, SeriesCoverRepo seriesCoverRepo) {
        this.userRepo = userRepo;
        this.illustrationRepo = illustrationRepo;
        this.articleRepo = articleRepo;
        this.seriesRepo = seriesRepo;
        this.tagRepo = tagRepo;
        this.commentRepo = commentRepo;
        this.avatarRepo = avatarRepo;
        this.seriesCoverRepo = seriesCoverRepo;
    }

    @Override
    public UserEntity getUserOrNotFound(String username) throws EntityNotFoundException {
        return Optional.ofNullable(userRepo.findByUsername(username))
                .orElseThrow(() -> new EntityNotFoundException("User " + username));
    }

    @Override
    public UserEntity getUserOrNorFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(userRepo, id, "User");
    }

    @Override
    public ArticleEntity getArticleOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(articleRepo, id, "Article");
    }

    @Override
    public IllustrationEntity getIllustrationOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(illustrationRepo, id, "Illustration");
    }

    @Override
    public CommentEntity getCommentOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(commentRepo, id, "Comment");
    }

    @Override
    public SeriesEntity getSeriesOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(seriesRepo, id, "Series");
    }

    @Override
    public TagEntity getTagOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(tagRepo, id, "Tag");
    }

    @Override
    public AvatarEntity getAvatarOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(avatarRepo, id, "Avatar ");
    }

    @Override
    public SeriesCoverEntity getSeriesCoverOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(seriesCoverRepo, id, "Series cover");
    }

    private <T> T findOrThrow(Optional<T> entity, String entityName, Long id) throws EntityNotFoundException {
        return entity.orElseThrow(() -> new EntityNotFoundException(entityName + " " + id));
    }

    private <T> T findByIdOrNotFound(JpaRepository<T, Long> repo, Long id, String entityName) throws EntityNotFoundException {
        return findOrThrow(repo.findById(id), entityName, id);
    }

}
