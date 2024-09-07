package com.gaotianchi.resourceservice.service.userservice;

import com.gaotianchi.resourceservice.persistence.entity.ImageEntity;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import com.gaotianchi.resourceservice.persistence.repo.UserRepo;
import com.gaotianchi.resourceservice.service.EntityFounderService;
import com.gaotianchi.resourceservice.web.error.EntityAlreadyExistException;
import com.gaotianchi.resourceservice.web.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.SeriesResponse;
import com.gaotianchi.resourceservice.web.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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
        UserEntity userEntity = userRepo.findByEmail(username);
        if (userEntity != null) throw new EntityAlreadyExistException(username);
        userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPenName(penName);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse updateInfo(String username, String penName) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        userEntity.setPenName(penName);
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
}
