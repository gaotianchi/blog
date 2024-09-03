package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.error.ArticleNotFoundException;
import com.gaotianchi.resourceservice.error.CommentNotFoundException;
import com.gaotianchi.resourceservice.error.UserNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.ArticleEntity;
import com.gaotianchi.resourceservice.persistence.entity.CommentEntity;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import com.gaotianchi.resourceservice.persistence.enums.CommentStatus;
import com.gaotianchi.resourceservice.persistence.repo.ArticleRepo;
import com.gaotianchi.resourceservice.persistence.repo.CommentRepo;
import com.gaotianchi.resourceservice.persistence.repo.UserRepo;
import com.gaotianchi.resourceservice.web.response.CommentOtd;
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

    @Autowired
    public CommentService(UserRepo userRepo, ArticleRepo articleRepo, CommentRepo commentRepo, CommentCacheService commentCacheService) {
        this.userRepo = userRepo;
        this.articleRepo = articleRepo;
        this.commentRepo = commentRepo;
        this.commentCacheService = commentCacheService;
    }

    public CommentEntity newComment(String body, Long userId, Long articleId, Optional<Long> parentCommentId) throws UserNotFoundException, ArticleNotFoundException, CommentNotFoundException {
        UserEntity userEntity = getUserOrNotFound(userId);
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setArticle(articleEntity);
        commentEntity.setAuthor(userEntity);
        commentEntity.setBody(body);
        if (parentCommentId.isPresent()) {
            CommentEntity parentComment = getCommentOrNotFound(parentCommentId.get());
            commentEntity.setParentComment(parentComment);
        }
        commentEntity.setCommentStatus(CommentStatus.PUBLISHED);
        commentEntity.setCreationDatetime(OffsetDateTime.now());
        commentEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        return commentRepo.save(commentEntity);
    }

    public void deleteComment(Long id) throws CommentNotFoundException {
        CommentEntity commentEntity = getCommentOrNotFound(id);
        commentEntity.setCommentStatus(CommentStatus.DELETED);
        commentEntity.setBody(null);
        commentRepo.save(commentEntity);
    }

    public CommentEntity updateCommentContent(Long id, String body) throws CommentNotFoundException {
        CommentEntity commentEntity = getCommentOrNotFound(id);
        commentEntity.setBody(body);
        commentEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        return commentRepo.save(commentEntity);
    }

    public CommentOtd getCommentOtd(CommentEntity commentEntity) {
        CommentOtd commentOtd = new CommentOtd(commentEntity);
        commentOtd.setLike(commentCacheService.getNumberOfCommentLike(commentEntity.getId()));
        commentOtd.setDislike(commentCacheService.getNumberOfCommentDislike(commentEntity.getId()));
        return commentOtd;
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

    public CommentWithRepliesOtd getCommentWithReplies(Long id) throws CommentNotFoundException {
        CommentEntity commentEntity = getCommentOrNotFound(id);
        CommentWithRepliesOtd commentWithRepliesOtd = new CommentWithRepliesOtd(commentEntity);
        setCommentLikeAndDislikeNumber(commentWithRepliesOtd);
        return commentWithRepliesOtd;
    }
    public CommentWithRepliesOtd getCommentWithReplies(CommentEntity commentEntity) throws CommentNotFoundException {
        CommentWithRepliesOtd commentWithRepliesOtd = new CommentWithRepliesOtd(commentEntity);
        setCommentLikeAndDislikeNumber(commentWithRepliesOtd);
        return commentWithRepliesOtd;
    }

    public UserEntity getUserOrNotFound(Long id) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        if (userEntity.isEmpty()) throw new UserNotFoundException();
        return userEntity.get();
    }

    public ArticleEntity getArticleOrNotFound(Long id) throws ArticleNotFoundException {
        Optional<ArticleEntity> articleEntity = articleRepo.findById(id);
        if (articleEntity.isEmpty()) throw new ArticleNotFoundException();
        return articleEntity.get();
    }

    public CommentEntity getCommentOrNotFound(Long id) throws CommentNotFoundException {
        Optional<CommentEntity> commentEntity = commentRepo.findById(id);
        if (commentEntity.isEmpty()) throw new CommentNotFoundException();
        return commentEntity.get();
    }
}
