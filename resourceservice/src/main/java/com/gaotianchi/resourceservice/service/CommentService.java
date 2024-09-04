package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.error.CommentNotFoundException;
import com.gaotianchi.resourceservice.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.ArticleEntity;
import com.gaotianchi.resourceservice.persistence.entity.CommentEntity;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import com.gaotianchi.resourceservice.persistence.enums.CommentStatus;
import com.gaotianchi.resourceservice.persistence.repo.ArticleRepo;
import com.gaotianchi.resourceservice.persistence.repo.CommentRepo;
import com.gaotianchi.resourceservice.persistence.repo.UserRepo;
import com.gaotianchi.resourceservice.web.response.CommentResponse;
import com.gaotianchi.resourceservice.web.response.CommentWithRepliesOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class CommentService {
    private final UserRepo userRepo;
    private final ArticleRepo articleRepo;
    private final CommentRepo commentRepo;
    private final CommentCacheService commentCacheService;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;

    @Autowired
    public CommentService(UserRepo userRepo, ArticleRepo articleRepo, CommentRepo commentRepo, CommentCacheService commentCacheService, EntityFounderService entityFounderService, EntityBelongService entityBelongService) {
        this.userRepo = userRepo;
        this.articleRepo = articleRepo;
        this.commentRepo = commentRepo;
        this.commentCacheService = commentCacheService;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
    }

    public CommentResponse newComment(String email, String body, Long articleId, Optional<Long> parentCommentId) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setArticle(articleEntity);
        commentEntity.setAuthor(userEntity);
        commentEntity.setBody(body);
        if (parentCommentId.isPresent()) {
            CommentEntity parentComment = entityFounderService.getCommentOrNotFound(parentCommentId.get());
            commentEntity.setParentComment(parentComment);
        }
        commentEntity.setCommentStatus(CommentStatus.PUBLISHED);
        commentEntity.setCreationDatetime(OffsetDateTime.now());
        commentEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        commentEntity = commentRepo.save(commentEntity);
        return new CommentResponse(commentEntity);
    }

    public CommentResponse getCommentTree(Long id) throws EntityNotFoundException {
        CommentEntity commentEntity = entityFounderService.getCommentOrNotFound(id);
        return new CommentResponse(commentEntity, true);
    }


    public void deleteComment(String email, Long id) throws EntityNotFoundException {
        CommentEntity commentEntity = entityBelongService.commentBelongToUser(email, id);
        commentEntity.setCommentStatus(CommentStatus.DELETED);
        commentEntity.setBody(null);
        commentRepo.save(commentEntity);
    }

    public CommentResponse updateCommentContent(String email, Long id, String body) throws EntityNotFoundException {
        CommentEntity commentEntity = entityBelongService.commentBelongToUser(email, id);
        commentEntity.setBody(body);
        commentEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        commentEntity = commentRepo.save(commentEntity);
        return new CommentResponse(commentEntity);
    }

    public void setCommentLikeAndDislikeNumber(CommentWithRepliesOtd commentWithRepliesOtd) {
        commentWithRepliesOtd.setDislike(commentCacheService.getNumberOfCommentLike(commentWithRepliesOtd.getId()));
        commentWithRepliesOtd.setLike(commentCacheService.getNumberOfCommentLike(commentWithRepliesOtd.getId()));
        if (!commentWithRepliesOtd.getCommentWithRepliesOtds().isEmpty()) {
            for (CommentWithRepliesOtd commentWithRepliesOtd1 : commentWithRepliesOtd.getCommentWithRepliesOtds()) {
                setCommentLikeAndDislikeNumber(commentWithRepliesOtd1);
            }
        }
    }

    public CommentWithRepliesOtd getCommentWithReplies(CommentEntity commentEntity) throws CommentNotFoundException {
        CommentWithRepliesOtd commentWithRepliesOtd = new CommentWithRepliesOtd(commentEntity);
        setCommentLikeAndDislikeNumber(commentWithRepliesOtd);
        return commentWithRepliesOtd;
    }
}
