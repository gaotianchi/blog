package com.gaotianchi.oauth2service.service.userservice;

import com.gaotianchi.oauth2service.persistence.entity.UserEntity;
import com.gaotianchi.oauth2service.persistence.enums.AccountStatus;
import com.gaotianchi.oauth2service.persistence.enums.RoleType;
import com.gaotianchi.oauth2service.persistence.repo.UserRepo;
import com.gaotianchi.oauth2service.service.EntityFounderService;
import com.gaotianchi.oauth2service.web.error.EntityAlreadyExistException;
import com.gaotianchi.oauth2service.web.error.EntityNotFoundException;
import com.gaotianchi.oauth2service.web.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collections;

@Service
public class UserService implements UserServiceInterface {
    private final EntityFounderService entityFounderService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Autowired
    public UserService(EntityFounderService entityFounderService, PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.entityFounderService = entityFounderService;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public UserResponse newUser(String username, String password) {
        if (entityFounderService.userExists(username))
            throw new EntityAlreadyExistException("User " + username);
        UserEntity userEntity = new UserEntity();
        userEntity.setAccountStatus(AccountStatus.ACTIVATED);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setUsername(username);
        userEntity.setRegistrationDateTime(OffsetDateTime.now());
        userEntity.setRoles(Collections.singletonList(entityFounderService.getRoleOrNotFound(RoleType.SUBSCRIBER)));
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse updateUsername(String username, String newUsername) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        userEntity.setUsername(newUsername);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse updatePassword(String username, String newPassword) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        userEntity.setUsername(newPassword);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse deregister(String username) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        userEntity.setAccountStatus(AccountStatus.DEREGISTERED);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse lockUser(Long userId) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(userId);
        userEntity.setAccountStatus(AccountStatus.LOCKED);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse unlockUser(Long userId) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(userId);
        userEntity.setAccountStatus(AccountStatus.ACTIVATED);
        userEntity = userRepo.save(userEntity);
        return new UserResponse(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return entityFounderService.getUserOrNotFound(username);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException(e.getLocalizedMessage());
        }
    }
}
