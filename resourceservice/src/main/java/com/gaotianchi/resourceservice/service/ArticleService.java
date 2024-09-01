package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.entity.ArticleEntity;
import com.gaotianchi.resourceservice.entity.CommentEntity;
import com.gaotianchi.resourceservice.entity.UserEntity;
import com.gaotianchi.resourceservice.enums.ArticleStatus;
import com.gaotianchi.resourceservice.repo.ArticleRepo;
import com.gaotianchi.resourceservice.repo.UserRepo;
import com.gaotianchi.resourceservice.web.error.ArticleNotFoundException;
import com.gaotianchi.resourceservice.web.error.CommentNotFoundException;
import com.gaotianchi.resourceservice.web.error.UnExpectedStatusException;
import com.gaotianchi.resourceservice.web.error.UserNotFoundException;
import com.gaotianchi.resourceservice.web.otd.ArticleCommentsOtd;
import com.gaotianchi.resourceservice.web.otd.CommentWithRepliesOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;
    private final UserRepo userRepo;
    private final CommentService commentService;

    @Autowired
    public ArticleService(ArticleRepo articleRepo, UserRepo userRepo, CommentService commentService) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
        this.commentService = commentService;
    }

    public ArticleEntity createNewDraft(Long userId) throws UserNotFoundException {
        Optional<UserEntity> author = userRepo.findById(userId);
        if (author.isEmpty()) throw new UserNotFoundException();
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setAuthor(author.get());
        articleEntity = articleRepo.save(articleEntity);
        articleEntity.setSlug("Post_" + articleEntity.getId());
        articleEntity.setArticleStatus(ArticleStatus.DRAFT);
        articleEntity.setCreationDatetime(OffsetDateTime.now());
        articleEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        return articleRepo.save(articleEntity);
    }

    public ArticleEntity throwInTrashCan(Long articleId) throws ArticleNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        articleEntity.setArticleStatus(ArticleStatus.TRASH);
        return articleRepo.save(articleEntity);
    }

    public void deleteTrash(Long articleId) throws ArticleNotFoundException, UnExpectedStatusException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        if (articleEntity.getArticleStatus() != ArticleStatus.TRASH) throw new UnExpectedStatusException();
        articleRepo.delete(articleEntity);
    }

    public ArticleEntity publishDraft(Long articleId) throws ArticleNotFoundException, UnExpectedStatusException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        if (articleEntity.getArticleStatus() != ArticleStatus.DRAFT) throw new UnExpectedStatusException();
        articleEntity.setArticleStatus(ArticleStatus.PUBLISHED);
        articleEntity.setPublishDatetime(OffsetDateTime.now());
        return articleRepo.save(articleEntity);
    }


    public ArticleEntity convertToDraft(Long articleId) throws UnExpectedStatusException, ArticleNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        if (articleEntity.getArticleStatus() == ArticleStatus.DRAFT ) throw new UnExpectedStatusException();
        articleEntity.setArticleStatus(ArticleStatus.DRAFT);
        return articleRepo.save(articleEntity);
    }

    public ArticleEntity getArticleOrNotFound(Long articleId) throws ArticleNotFoundException {
        Optional<ArticleEntity> article = articleRepo.findById(articleId);
        if (article.isEmpty()) throw new ArticleNotFoundException();
        return article.get();
    }

    public ArticleEntity updateArticleContent(Long articleId, String title, String body, String summary, String slug) throws ArticleNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(articleId);
        articleEntity.setTitle(title);
        articleEntity.setSlug(slug);
        articleEntity.setBody(body);
        articleEntity.setSummary(summary);
        articleEntity.setLastUpdatedDatetime(OffsetDateTime.now());
        return articleRepo.save(articleEntity);
    }

    public ArticleCommentsOtd getArticleCommentsOtd(Long id) throws CommentNotFoundException, ArticleNotFoundException {
        ArticleEntity articleEntity = getArticleOrNotFound(id);
        ArticleCommentsOtd articleCommentsOtd = new ArticleCommentsOtd();
        List<CommentWithRepliesOtd> commentWithRepliesOtds = new ArrayList<>();
        if (articleEntity.getCommentEntities().isEmpty()) {
            articleCommentsOtd.setCommentWithRepliesOtds(commentWithRepliesOtds);
            return articleCommentsOtd;
        }
        for (CommentEntity commentEntity : articleEntity.getCommentEntities()) {
            if (commentEntity.getParentComment() == null) {
                CommentWithRepliesOtd commentWithRepliesOtd = commentService.getCommentWithReplies(commentEntity);
                commentWithRepliesOtds.add(commentWithRepliesOtd);
            }
        }
        articleCommentsOtd.setCommentWithRepliesOtds(commentWithRepliesOtds);
        return articleCommentsOtd;
    }
}
