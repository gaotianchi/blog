package com.gaotianchi.resourceservice.service.commentservice;

import com.gaotianchi.resourceservice.persistence.entity.ArticleEntity;
import com.gaotianchi.resourceservice.persistence.entity.CommentEntity;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import com.gaotianchi.resourceservice.persistence.enums.CommentStatus;
import com.gaotianchi.resourceservice.persistence.repo.CommentRepo;
import com.gaotianchi.resourceservice.service.EntityBelongService;
import com.gaotianchi.resourceservice.service.EntityFounderService;
import com.gaotianchi.resourceservice.web.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.web.response.CommentResponse;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CommentService implements CommentServiceInterface {
    private final CommentRepo commentRepo;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;

    @Autowired
    public CommentService(CommentRepo commentRepo, EntityFounderService entityFounderService, EntityBelongService entityBelongService) {
        this.commentRepo = commentRepo;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
    }

    @Override
    public CommentResponse newComment(String email, String body, Long articleId, @Nullable Long parentCommentId) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setArticle(articleEntity);
        commentEntity.setAuthor(userEntity);
        commentEntity.setBody(body);
        if (parentCommentId != null) {
            CommentEntity parentComment = entityFounderService.getCommentOrNotFound(parentCommentId);
            commentEntity.setParentComment(parentComment);
        }
        commentEntity.setCommentStatus(CommentStatus.PUBLISHED);
        commentEntity.setCreationDatetime(OffsetDateTime.now());
        commentEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        commentEntity = commentRepo.save(commentEntity);
        return new CommentResponse(commentEntity);
    }

    @Override
    public CommentResponse getCommentTree(Long id) throws EntityNotFoundException {
        CommentEntity commentEntity = entityFounderService.getCommentOrNotFound(id);
        return new CommentResponse(commentEntity, true);
    }

    @Override
    public void deleteComment(String email, Long id) throws EntityNotFoundException {
        CommentEntity commentEntity = entityBelongService.commentBelongToUser(email, id);
        commentEntity.setCommentStatus(CommentStatus.DELETED);
        commentEntity.setBody(null);
        commentRepo.save(commentEntity);
    }

    @Override
    public CommentResponse updateContent(String email, Long id, String body) throws EntityNotFoundException {
        CommentEntity commentEntity = entityBelongService.commentBelongToUser(email, id);
        commentEntity.setBody(body);
        commentEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        commentEntity = commentRepo.save(commentEntity);
        return new CommentResponse(commentEntity);
    }
}
