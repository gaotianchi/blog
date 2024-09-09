package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.LevelEntity;
import com.gaotianchi.resource.persistence.enums.LevelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepo extends JpaRepository<LevelEntity, Long> {
    LevelEntity findByLevel(LevelType levelType);
}
