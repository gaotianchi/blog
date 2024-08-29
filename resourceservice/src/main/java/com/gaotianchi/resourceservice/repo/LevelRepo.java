package com.gaotianchi.resourceservice.repo;

import com.gaotianchi.resourceservice.entity.LevelEntity;
import com.gaotianchi.resourceservice.enums.LevelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepo extends JpaRepository<LevelEntity, Long> {
    LevelEntity findByLevel(LevelType levelType);
}
