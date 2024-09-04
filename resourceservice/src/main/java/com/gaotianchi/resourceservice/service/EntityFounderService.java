package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.persistence.entity.*;
import com.gaotianchi.resourceservice.persistence.repo.*;
import com.gaotianchi.resourceservice.web.error.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public UserEntity getUserOrNotFound(Long id) throws EntityNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        if (userEntity.isEmpty()) throw new EntityNotFoundException("User " + id);
        return userEntity.get();
    }

    public UserEntity getUserOrNotFound(String email) throws EntityNotFoundException {
        Optional<UserEntity> userEntity = Optional.ofNullable(userRepo.findByEmail(email));
        if (userEntity.isEmpty()) throw new EntityNotFoundException("User " + email);
        return userEntity.get();
    }

    public ArticleEntity getArticleOrNotFound(Long id) throws EntityNotFoundException {
        Optional<ArticleEntity> articleEntity = articleRepo.findById(id);
        if (articleEntity.isEmpty()) throw new EntityNotFoundException("Article " + id);
        return articleEntity.get();
    }

    public CommentEntity getCommentOrNotFound(Long id) throws EntityNotFoundException {
        Optional<CommentEntity> commentEntity = commentRepo.findById(id);
        if (commentEntity.isEmpty()) throw new EntityNotFoundException("Comment " + id);
        return commentEntity.get();
    }

    public ImageEntity getImageOrNotFound(Long imageId) throws EntityNotFoundException {
        Optional<ImageEntity> imageEntity = imageRepo.findById(imageId);
        if (imageEntity.isEmpty()) throw new EntityNotFoundException("Image " + imageId);
        return imageEntity.get();
    }

    public SeriesEntity getSeriesOrNotFound(Long id) throws EntityNotFoundException {
        Optional<SeriesEntity> seriesEntity = seriesRepo.findById(id);
        if (seriesEntity.isEmpty()) throw new EntityNotFoundException("Series " + id);
        return seriesEntity.get();
    }

    public TagEntity getTagOrNotFound(Long id) throws EntityNotFoundException {
        Optional<TagEntity> tagEntity = tagRepo.findById(id);
        if (tagEntity.isEmpty()) throw new EntityNotFoundException("Tag " + id);
        return tagEntity.get();
    }
}
