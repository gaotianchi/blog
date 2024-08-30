package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.entity.UserEntity;
import com.gaotianchi.resourceservice.repo.UserRepo;
import com.gaotianchi.resourceservice.web.error.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserEntity createNewUser(String email) throws UserAlreadyExistException {
        UserEntity userEntity = userRepo.findByEmail(email);
        if (userEntity != null) throw new UserAlreadyExistException();
        userEntity = new UserEntity();
        userEntity.setEmail(email);

        return userRepo.save(userEntity);
    }
}
