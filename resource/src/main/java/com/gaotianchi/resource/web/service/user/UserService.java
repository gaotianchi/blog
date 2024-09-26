package com.gaotianchi.resource.web.service.user;

import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.UserRepo;
import com.gaotianchi.resource.web.response.info.UserInfo;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

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
    public UserInfo getInfo(Long id) {
        UserEntity userEntity = entityFounderService.getUserOrNorFound(id);
        return new UserInfo(userEntity);
    }
}
