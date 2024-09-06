package com.gaotianchi.oauth2service.persistence.repo;

import com.gaotianchi.oauth2service.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
