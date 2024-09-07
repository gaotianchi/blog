package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.persistence.entity.*;
import com.gaotianchi.resourceservice.persistence.repo.*;
import com.gaotianchi.resourceservice.web.error.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntityFounderService {
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;
    private final ArticleRepo articleRepo;
    private final SeriesRepo seriesRepo;
    private final TagRepo tagRepo;
    private final CommentRepo commentRepo;

    @Autowired
    public EntityFounderService(UserRepo userRepo, ImageRepo imageRepo, ArticleRepo articleRepo, SeriesRepo seriesRepo, TagRepo tagRepo, CommentRepo commentRepo) {
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
        this.articleRepo = articleRepo;
        this.seriesRepo = seriesRepo;
        this.tagRepo = tagRepo;
        this.commentRepo = commentRepo;
    }

    private <T> T findOrThrow(Optional<T> entity, String entityName, Long id) throws EntityNotFoundException {
        return entity.orElseThrow(() -> new EntityNotFoundException(entityName + " " + id));
    }

    private <T> T findByIdOrNotFound(JpaRepository<T, Long> repo, Long id, String entityName) throws EntityNotFoundException {
        return findOrThrow(repo.findById(id), entityName, id);
    }

    public UserEntity getUserOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(userRepo, id, "User");
    }

    public UserEntity getUserOrNotFound(String username) throws EntityNotFoundException {
        return Optional.ofNullable(userRepo.findByUsername(username))
                .orElseThrow(() -> new EntityNotFoundException("User " + username));
    }

    public ArticleEntity getArticleOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(articleRepo, id, "Article");
    }

    public CommentEntity getCommentOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(commentRepo, id, "Comment");
    }

    public ImageEntity getImageOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(imageRepo, id, "Image");
    }

    public SeriesEntity getSeriesOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(seriesRepo, id, "Series");
    }

    public TagEntity getTagOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(tagRepo, id, "Tag");
    }
}
