package com.gaotianchi.resource.web.service.article;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.enums.ArticleStatus;
import com.gaotianchi.resource.persistence.repo.ArticleRepo;
import com.gaotianchi.resource.persistence.repo.IllustrationRepo;
import com.gaotianchi.resource.persistence.repo.TagRepo;
import com.gaotianchi.resource.web.response.info.ArticleInfo;
import com.gaotianchi.resource.web.response.info.IllustrationInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;
import com.gaotianchi.resource.web.response.info.TagInfo;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ArticleService implements ArticleServiceInterface {
    private final ArticleRepo articleRepo;
    private final TagRepo tagRepo;
    private final EntityFounderService entityFounderService;
    private final EntityBelongService entityBelongService;
    private final IllustrationRepo illustrationRepo;

    @Autowired
    public ArticleService(ArticleRepo articleRepo, TagRepo tagRepo, EntityFounderService entityFounderService, EntityBelongService entityBelongService, IllustrationRepo illustrationRepo) {
        this.articleRepo = articleRepo;
        this.tagRepo = tagRepo;
        this.entityFounderService = entityFounderService;
        this.entityBelongService = entityBelongService;
        this.illustrationRepo = illustrationRepo;
    }

    @Override
    public ArticleInfo newArticle(String username) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setUser(userEntity);
        articleEntity = articleRepo.save(articleEntity);
        articleEntity.setSlug("Post_" + articleEntity.getId());
        articleEntity.setStatus(ArticleStatus.DRAFT);
        articleEntity.setCreationDatetime(OffsetDateTime.now());
        articleEntity.setUpdateDatetime(OffsetDateTime.now());
        articleEntity = articleRepo.save(articleEntity);
        return new ArticleInfo(articleEntity);
    }

    @Override
    public void deleteArticle(String username, Long id) {
        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
        // 取消关联
        // 删除相关文件
        // 删除相关实体
        SeriesEntity seriesEntity = articleEntity.getSeries();

    }

    @Override
    public ArticleInfo getInfo(String username, Long id) {
        return null;
    }

    @Override
    public void updateStatus(String username, Long id, String newStatus) throws Exception {

    }

    @Override
    public void updateTitle(String username, Long id, String newTitle) {

    }

    @Override
    public void updateSummary(String username, Long id, String newSummary) {

    }

    @Override
    public void updateSlug(String username, Long id, String newSlug) {

    }

    @Override
    public void updateBody(String username, Long id, String newBody) {

    }

    @Override
    public String getBody(String username, Long id) {
        return "";
    }

    @Override
    public SeriesInfo setSeries(String username, Long id, Long seriesId) {
        return null;
    }

    @Override
    public void removeSeries(String username, Long id) {

    }

    @Override
    public TagInfo addTag(String username, Long id, Long tagId) {
        return null;
    }

    @Override
    public void removeTag(String username, Long tagId, Long id) {

    }

    @Override
    public List<TagInfo> getTagList(String username, Long id) {
        return List.of();
    }

    @Override
    public IllustrationInfo addIllustration(String username, Long id, Long illustrationId) {
        return null;
    }

    @Override
    public void removeIllustration(String username, Long id, Long illustrationId) {

    }

//
//    @Override
//    public ArticleInfo getInfo(String username, Long id) {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, id);
//        return new ArticleInfo(articleEntity);
//    }
//
//    @Override
//    public void updateStatus(String username, Long articleId, String newStatus) throws Exception {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        ArticleStatus from = articleEntity.getStatus();
//        ArticleStatus to = ArticleStatus.valueOf(newStatus);
//        if (!changeStatusIsPermitted(from, to)) throw new Exception("Invalid target status.");
//        articleEntity.setStatus(to);
//        if (to.equals(ArticleStatus.PUBLISHED)) {
//            articleEntity.setPublishDatetime(OffsetDateTime.now());
//        }
//        articleEntity.setUpdateDatetime(OffsetDateTime.now());
//        articleRepo.save(articleEntity);
//    }
//
//    @Override
//    public void updateSummary(String username, Long articleId, String newSummary) {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        articleEntity.setSummary(newSummary);
//        articleRepo.save(articleEntity);
//    }
//
//    @Override
//    public void updateSlug(String username, Long articleId, String newSlug) {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        articleEntity.setSummary(newSlug);
//        articleRepo.save(articleEntity);
//    }
//
//    @Override
//    public void updateBody(String username, Long articleId, String newTitle, String newBody) {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        articleEntity.setTitle(newTitle);
//        articleEntity.setBody(newBody);
//        articleRepo.save(articleEntity);
//    }
//
//    @Override
//    public ArticleContent getBody(String username, Long articleId) {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        return new ArticleContent(articleEntity);
//    }
//
//    @Override
//    public SeriesInfo setSeries(String username, Long articleId, Long seriesId) throws EntityNotFoundException {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        SeriesEntity seriesEntity = entityBelongService.seriesBelongToUser(username, seriesId);
//        articleEntity.setSeries(seriesEntity);
//        articleEntity = articleRepo.save(articleEntity);
//        return new SeriesInfo(articleEntity.getSeries());
//    }
//
//    @Override
//    public void removeSeries(String username, Long articleId) throws EntityNotFoundException {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        articleEntity.setSeries(null);
//        articleRepo.save(articleEntity);
//    }
//
//    @Override
//    public ImageInfo setCover(String username, Long articleId, Long imageId) throws EntityNotFoundException {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        IllustrationEntity illustrationEntity = entityBelongService.imageBelongToUser(username, imageId);
//        illustrationEntity.setForArticle(true);
//        articleEntity.setCover(illustrationRepo.save(illustrationEntity));
//        articleEntity = articleRepo.save(articleEntity);
//        return null;
//    }
//
//    @Override
//    public void removeCover(String username, Long articleId) throws EntityNotFoundException {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        articleEntity.setCover(null);
//        articleRepo.save(articleEntity);
//    }
//
//    @Override
//    public TagResponse addTag(String username, Long articleId, Long tagId) throws EntityNotFoundException {
//        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        Collection<TagEntity> tagEntities = articleEntity.getTagList();
//        Collection<ArticleEntity> articleEntities = tagEntity.getArticleList();
//        if (!tagEntities.contains(tagEntity)) tagEntities.add(tagEntity);
//        if (!articleEntities.contains(articleEntity)) articleEntities.add(articleEntity);
//        articleEntity.setTagList(tagEntities);
//        tagEntity.setArticleList(articleEntities);
//        articleRepo.save(articleEntity);
//        tagEntity = tagRepo.save(tagEntity);
//        return new TagResponse(tagEntity);
//    }
//
//    @Override
//    public void removeTag(String username, Long tagId, Long articleId) throws EntityNotFoundException {
//        TagEntity tagEntity = entityFounderService.getTagOrNotFound(tagId);
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        Collection<TagEntity> tagEntities = articleEntity.getTagList();
//        Collection<ArticleEntity> articleEntities = tagEntity.getArticleList();
//        articleEntities.remove(articleEntity);
//        tagEntities.remove(tagEntity);
//        articleEntity.setTagList(tagEntities);
//        articleRepo.save(articleEntity);
//        tagRepo.save(tagEntity);
//    }
//
//    @Override
//    public List<TagInfo> getTagList(String username, Long articleId) {
//        return List.of();
//    }
//
//    @Override
//    public ImageResponse addIllustration(String username, Long articleId, Long imageId) throws EntityNotFoundException {
//        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(userEntity, articleId);
//        IllustrationEntity illustrationEntity = entityBelongService.imageBelongToUser(userEntity, imageId);
//        if (articleEntity.getIllustrationList().add(illustrationEntity)) {
//            articleRepo.save(articleEntity);
//        }
//        if (illustrationEntity.getArticleList().add(articleEntity)) {
//            illustrationRepo.save(illustrationEntity);
//        }
//        return new ImageResponse(illustrationEntity);
//    }
//
//    @Override
//    public void removeIllustration(String username, Long articleId, Long illustrationId) throws EntityNotFoundException {
//        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(userEntity, articleId);
//        IllustrationEntity illustrationEntity = entityBelongService.imageBelongToUser(userEntity, illustrationId);
//        if (articleEntity.getIllustrationList().remove(illustrationEntity)) {
//            articleRepo.save(articleEntity);
//        }
//        if (illustrationEntity.getArticleList().remove(articleEntity)) {
//            illustrationRepo.save(illustrationEntity);
//        }
//    }
//
//    @Override
//    public List<ImageInfo> getIllustrationList(String username, Long articleId) {
//        return List.of();
//    }
//
//    @Override
//    public PageArticle getPageArticleList(String username, Integer page) throws EntityNotFoundException {
//        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
//        Pageable pageable = PageRequest.of(page, 10);
//        Page<ArticleEntity> articleEntityPage = articleRepo.findByAuthorOrderByCreationDatetimeDesc(userEntity, pageable);
//        List<ArticleResponse> articleResponses = articleEntityPage.getContent().stream()
//                .map(ArticleResponse::new)
//                .collect(Collectors.toList());
//        return new PageArticle(articleResponses, articleEntityPage.getTotalPages(), page);
//    }
//
//    @Override
//    public ArticleResponse updateContent(String username, Long articleId, String title, String body, String summary, String slug) throws EntityNotFoundException {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        articleEntity.setTitle(title);
//        articleEntity.setSlug(slug);
//        articleEntity.setBody(body);
//        articleEntity.setSummary(summary);
//        articleEntity.setUpdateDatetime(OffsetDateTime.now());
//        articleEntity = articleRepo.save(articleEntity);
//        return new ArticleResponse(articleEntity);
//    }
//
//    @Override
//    public List<TagResponse> listTags(Long id) throws EntityNotFoundException {
//        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(id);
//        List<TagResponse> tagResponses = new ArrayList<>();
//        for (TagEntity tagEntity : articleEntity.getTagList()) {
//            TagResponse tagResponse = new TagResponse(tagEntity);
//            tagResponses.add(tagResponse);
//        }
//        return tagResponses;
//    }
//
//    @Override
//    public List<CommentResponse> listArticleComments(Long articleId) throws EntityNotFoundException {
//        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
//        List<CommentResponse> commentResponses = new ArrayList<>();
//        for (CommentEntity commentEntity : articleEntity.getCommentList()) {
//            if (commentEntity.getParentComment() == null) {
//                commentResponses.add(commentService.getCommentTree(commentEntity.getId()));
//            }
//        }
//        return commentResponses;
//    }
//
//
//
//    @SneakyThrows
//    @Override
//    public List<ImageResponse> listArticleImages(Long articleId) throws EntityNotFoundException {
//        ArticleEntity articleEntity = entityFounderService.getArticleOrNotFound(articleId);
//        return articleEntity.getIllustrationList().stream().map(ImageResponse::new).collect(Collectors.toList());
//    }
//
//    @Override
//    public ArticleResponse getArticleMainContent(String username, Long articleId) {
//        ArticleEntity articleEntity = entityBelongService.articleBelongToUser(username, articleId);
//        return new ArticleResponse(articleEntity);
//    }
//
//    private boolean changeStatusIsPermitted(ArticleStatus from, ArticleStatus to) {
//        if (from.equals(to)) return false;
//        if (from.equals(ArticleStatus.PUBLISHED) && to.equals(ArticleStatus.TRASH)) return false;
//        return !from.equals(ArticleStatus.TRASH) || !to.equals(ArticleStatus.PUBLISHED);
//    }

}
