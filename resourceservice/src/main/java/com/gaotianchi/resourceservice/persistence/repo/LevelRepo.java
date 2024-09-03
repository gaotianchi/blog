package com.gaotianchi.resourceservice.persistence.repo;

import com.gaotianchi.resourceservice.persistence.entity.LevelEntity;
import com.gaotianchi.resourceservice.persistence.enums.LevelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepo extends JpaRepository<LevelEntity, Long> {
    LevelEntity findByLevel(LevelType levelType);
}
