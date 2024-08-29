package com.gaotianchi.resourceservice.repo;

import com.gaotianchi.resourceservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
}
