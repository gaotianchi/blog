package com.gaotianchi.resourceservice.event;

import com.gaotianchi.resourceservice.entity.BehaviorEntity;
import com.gaotianchi.resourceservice.entity.LevelEntity;
import com.gaotianchi.resourceservice.enums.BehaviorType;
import com.gaotianchi.resourceservice.enums.LevelType;
import com.gaotianchi.resourceservice.repo.BehaviorRepo;
import com.gaotianchi.resourceservice.repo.LevelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final LevelRepo levelRepo;
    private final BehaviorRepo behaviorRepo;

    @Autowired
    public SetupDataLoader(LevelRepo levelRepo, BehaviorRepo behaviorRepo) {
        this.levelRepo = levelRepo;
        this.behaviorRepo = behaviorRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (LevelType levelType : LevelType.values()) {
            createLevelIfNotFound(levelType);
        }
        for (BehaviorType behaviorType : BehaviorType.values()) {
            createBehaviorIfNotFound(behaviorType);
        }

    }

    private void createLevelIfNotFound(LevelType levelType) {
        LevelEntity levelEntity = levelRepo.findByLevel(levelType);
        if (levelEntity == null) {
            levelEntity = new LevelEntity(levelType);
            levelEntity.setScoreMilestones(levelType.getDefaultScoreMilestones());
            levelRepo.save(levelEntity);
        }
    }

    private void createBehaviorIfNotFound(BehaviorType behaviorType) {
        BehaviorEntity behaviorEntity = behaviorRepo.findByBehavior(behaviorType);
        if (behaviorEntity == null) {
            behaviorEntity = new BehaviorEntity(behaviorType);
            behaviorEntity.setStoreIncrement(behaviorType.getDefaultScoreIncrement());
            behaviorRepo.save(behaviorEntity);
        }
    }

}
