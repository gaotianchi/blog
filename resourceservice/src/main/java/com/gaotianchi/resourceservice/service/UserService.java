package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.error.UserAlreadyExistException;
import com.gaotianchi.resourceservice.error.UserNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import com.gaotianchi.resourceservice.persistence.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username);
    }
}
