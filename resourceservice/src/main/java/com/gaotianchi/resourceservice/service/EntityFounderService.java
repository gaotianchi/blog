package com.gaotianchi.resourceservice.service;

import com.gaotianchi.resourceservice.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.ImageEntity;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import com.gaotianchi.resourceservice.persistence.repo.ImageRepo;
import com.gaotianchi.resourceservice.persistence.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntityFounderService {
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;

    @Autowired
    public EntityFounderService(UserRepo userRepo, ImageRepo imageRepo) {
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
    }

    public UserEntity getUserOrNotFound(Long id) throws EntityNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        if (userEntity.isEmpty()) throw new EntityNotFoundException("User " + id);
        return userEntity.get();
    }

    public UserEntity getUserOrNotFound(String email) throws EntityNotFoundException {
        Optional<UserEntity> userEntity = Optional.ofNullable(userRepo.findByEmail(email));
        if (userEntity.isEmpty()) throw new EntityNotFoundException("User " + email);
        return userEntity.get();
    }


    public ImageEntity getImageOrNotFound(Long imageId) throws EntityNotFoundException {
        Optional<ImageEntity> imageEntity = imageRepo.findById(imageId);
        if (imageEntity.isEmpty()) throw new EntityNotFoundException("Image " + imageId);
        return imageEntity.get();
    }
}
