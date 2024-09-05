package com.gaotianchi.resourceservice.service.userservice;

import com.gaotianchi.resourceservice.persistence.entity.ImageEntity;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import com.gaotianchi.resourceservice.persistence.enums.AccountStatus;
import com.gaotianchi.resourceservice.persistence.enums.RoleType;
import com.gaotianchi.resourceservice.persistence.repo.RoleRepo;
import com.gaotianchi.resourceservice.persistence.repo.UserRepo;
import com.gaotianchi.resourceservice.service.EntityFounderService;
import com.gaotianchi.resourceservice.web.error.EntityAlreadyExistException;
import com.gaotianchi.resourceservice.web.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.SeriesResponse;
import com.gaotianchi.resourceservice.web.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService, UserServiceInterface {
    private final UserRepo userRepo;
    private final EntityFounderService entityFounderService;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepo userRepo, EntityFounderService entityFounderService, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.entityFounderService = entityFounderService;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse newUser(String penName, String email, String password) throws EntityAlreadyExistException {
        UserEntity userEntity = userRepo.findByEmail(email);
        if (userEntity != null) throw new EntityAlreadyExistException(email);
        userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPenName(penName);
        userEntity.setAccountStatus(AccountStatus.ACTIVATED);
        userEntity.setRoles(Collections.singletonList(roleRepo.findByRoleType(RoleType.SUBSCRIBER)));
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRegistrationDateTime(OffsetDateTime.now());
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse updateInfo(String email, String penName) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        userEntity.setPenName(penName);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse updateAvatar(String email, Long imageId) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        ImageEntity imageEntity = entityFounderService.getImageOrNotFound(imageId);
        userEntity.setAvatar(imageEntity);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity, true);
    }

    @Override
    public UserResponse resetPassword(String email, String newPassword) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse deregister(String email) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        userEntity.setAccountStatus(AccountStatus.DEREGISTERED);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse lockUser(Long userId) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(userId);
        userEntity.setAccountStatus(AccountStatus.LOCKED);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public List<UserResponse> listUsers() {
        Collection<UserEntity> userEntities = userRepo.findAll();
        return userEntities.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleResponse> listArticles(String email) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        return userEntity.getArticleEntities().stream().map(ArticleResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<SeriesResponse> listSeries(String email) throws EntityNotFoundException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(email);
        return userEntity.getSeriesEntities().stream().map(seriesEntity -> new SeriesResponse(seriesEntity, true)).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return entityFounderService.getUserOrNotFound(username);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("");
        }
    }


}