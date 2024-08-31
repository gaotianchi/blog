package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.entity.UserEntity;
import com.gaotianchi.resourceservice.repo.UserRepo;
import com.gaotianchi.resourceservice.web.error.UserAlreadyExistException;
import com.gaotianchi.resourceservice.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void deleteUserData(Long id) throws UserNotFoundException {
        UserEntity userEntity = getUserOrNotFound(id);
        userRepo.delete(userEntity);
    }

    public UserEntity getUserOrNotFound(Long id) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        if (userEntity.isEmpty()) throw new UserNotFoundException();
        return userEntity.get();
    }
}
