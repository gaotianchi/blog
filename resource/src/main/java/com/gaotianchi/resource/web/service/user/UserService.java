package com.gaotianchi.resource.web.service.user;

import com.gaotianchi.resource.persistence.entity.AvatarEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.IllustrationRepo;
import com.gaotianchi.resource.persistence.repo.UserRepo;
import com.gaotianchi.resource.web.response.info.AvatarInfo;
import com.gaotianchi.resource.web.response.info.UserInfo;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@Service
public class UserService implements UserServiceInterface {
    private final UserRepo userRepo;
    private final EntityFounderService entityFounderService;
    private final IllustrationRepo illustrationRepo;
    private final EntityBelongService entityBelongService;


    @Autowired
    public UserService(UserRepo userRepo, EntityFounderService entityFounderService, IllustrationRepo illustrationRepo, EntityBelongService entityBelongService) {
        this.userRepo = userRepo;
        this.entityFounderService = entityFounderService;
        this.illustrationRepo = illustrationRepo;
        this.entityBelongService = entityBelongService;
    }

    @Override
    public UserInfo newUser(String username, String penName, String profile, TimeZone timeZone) throws Exception {
        UserEntity userEntity = userRepo.findByUsername(username);
        if (userEntity != null) throw new Exception("User " + username + "has been created.");
        userEntity = new UserEntity();
        userEntity.setProfile(profile);
        userEntity.setUsername(username);
        userEntity.setTimeZone(timeZone);
        userEntity.setPenName(penName);
        return new UserInfo(userRepo.save(userEntity));
    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void updateInfo(String username, String newPenName, String newProfile, TimeZone newTimeZone) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        userEntity.setPenName(newPenName);
        userEntity.setProfile(newProfile);
        userEntity.setTimeZone(newTimeZone);
        userRepo.save(userEntity);
    }

    @Override
    public UserInfo getInfo(String username) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        return new UserInfo(userEntity);
    }

    @Override
    public AvatarInfo setAvatar(String username, Long avatarId) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        AvatarEntity avatarEntity = entityBelongService.avatarBelongToUser(username, avatarId);

    }

    @Override
    public AvatarInfo updateAvatar(String username, Long newAvatarId) {
        return null;
    }

    @Override
    public void removeAvatar(String username, Long avatarId) {

    }

//    @Override
//    public UserResponse newUser(String penName, String username) throws EntityAlreadyExistException {
//        UserEntity userEntity = userRepo.findByUsername(username);
//        if (userEntity != null) throw new EntityAlreadyExistException(username);
//        userEntity = new UserEntity();
//        userEntity.setUsername(username);
//        userEntity.setPenName(penName);
//        userEntity = userRepo.save(userEntity);
//        return new UserResponse(userEntity);
//    }
//
//    @Override
//    public UserResponse updateInfo(String username, String penName, String profile, Long avatarId, String timezone) throws EntityNotFoundException {
//        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
//        userEntity.setPenName(penName);
//        if (avatarId != null) {
//            IllustrationEntity illustrationEntity = entityFounderService.getImageOrNotFound(avatarId);
//            illustrationEntity.setForAvatar(true);
//            userEntity.setAvatar(illustrationRepo.save(illustrationEntity));
//        }
//        userEntity.setProfile(profile);
//        userEntity.setTimeZone(TimeZone.getTimeZone(timezone));
//        userEntity = userRepo.save(userEntity);
//        return new UserResponse(userEntity, true);
//    }
//
//    @Override
//    public UserResponse setAvatar(String username, Long imageId) throws EntityNotFoundException {
//        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
//        IllustrationEntity illustrationEntity = entityFounderService.getImageOrNotFound(imageId);
//        userEntity.setAvatar(illustrationEntity);
//        userEntity = userRepo.save(userEntity);
//        return new UserResponse(userEntity, true);
//    }
//
//
//    @Override
//    public List<UserResponse> listUsers() {
//        Collection<UserEntity> userEntities = userRepo.findAll();
//        return userEntities.stream()
//                .map(UserResponse::new)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ArticleResponse> listArticles(String username) throws EntityNotFoundException {
//        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
//        return userEntity.getArticleList().stream().map(ArticleResponse::new).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<SeriesResponse> listSeries(String username) throws EntityNotFoundException {
//        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
//        return userEntity.getSeriesList().stream().map(seriesEntity -> new SeriesResponse(seriesEntity, true)).collect(Collectors.toList());
//    }
//
//    @Override
//    public UserInfo getInfo(String username) {
//        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
//        return new UserResponse(userEntity, true);
//    }
}
