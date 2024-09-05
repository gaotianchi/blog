package com.gaotianchi.resourceservice.persistence.repo;

import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
