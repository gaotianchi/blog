package com.gaotianchi.resource.web.service.userservice;

import com.gaotianchi.resource.persistence.entity.ImageEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.UserRepo;
import com.gaotianchi.resource.web.error.EntityAlreadyExistException;
import com.gaotianchi.resource.web.error.EntityNotFoundException;
import com.gaotianchi.resource.web.response.ArticleResponse;
import com.gaotianchi.resource.web.response.SeriesResponse;
import com.gaotianchi.resource.web.response.UserResponse;
import com.gaotianchi.resource.web.service.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {
    private final UserRepo userRepo;
    private final EntityFounderService entityFounderService;


    @Autowired
    public UserService(UserRepo userRepo, EntityFounderService entityFounderService) {
        this.userRepo = userRepo;
        this.entityFounderService = entityFounderService;
    }

    @Override
    public UserResponse newUser(String penName, String username) throws EntityAlreadyExistException {
        UserEntity userEntity = userRepo.findByUsername(username);
        if (userEntity != null) throw new EntityAlreadyExistException(username);
        userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPenName(penName);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse updateInfo(String username, String penName, String profile, Long avatarId, String timezone) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        ImageEntity imageEntity = entityFounderService.getImageOrNotFound(avatarId);
        userEntity.setPenName(penName);
        userEntity.setAvatar(imageEntity);
        userEntity.setProfile(profile);
        userEntity.setTimeZone(TimeZone.getTimeZone(timezone));
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse updateAvatar(String username, Long imageId) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        ImageEntity imageEntity = entityFounderService.getImageOrNotFound(imageId);
        userEntity.setAvatar(imageEntity);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity, true);
    }


    @Override
    public List<UserResponse> listUsers() {
        Collection<UserEntity> userEntities = userRepo.findAll();
        return userEntities.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleResponse> listArticles(String username) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        return userEntity.getArticleEntities().stream().map(ArticleResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<SeriesResponse> listSeries(String username) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        return userEntity.getSeriesEntities().stream().map(seriesEntity -> new SeriesResponse(seriesEntity, true)).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserInfo(String username) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        return new UserResponse(userEntity, true);
    }
}
