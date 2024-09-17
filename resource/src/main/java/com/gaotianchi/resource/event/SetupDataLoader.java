package com.gaotianchi.resource.event;

import com.gaotianchi.resource.persistence.entity.BehaviorEntity;
import com.gaotianchi.resource.persistence.entity.LevelEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.enums.BehaviorType;
import com.gaotianchi.resource.persistence.enums.LevelType;
import com.gaotianchi.resource.persistence.repo.BehaviorRepo;
import com.gaotianchi.resource.persistence.repo.LevelRepo;
import com.gaotianchi.resource.persistence.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@Service
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final LevelRepo levelRepo;
    private final BehaviorRepo behaviorRepo;
    private final UserRepo userRepo;

    @Autowired
    public SetupDataLoader(LevelRepo levelRepo, BehaviorRepo behaviorRepo, UserRepo userRepo) {
        this.levelRepo = levelRepo;
        this.behaviorRepo = behaviorRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (LevelType levelType : LevelType.values()) {
            createLevelIfNotFound(levelType);
        }
        for (BehaviorType behaviorType : BehaviorType.values()) {
            createBehaviorIfNotFound(behaviorType);
        }
        createBloggerIfNotFound();

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

    private void createBloggerIfNotFound() {
        UserEntity userEntity = userRepo.findByUsername("6159984@gmail.com");
        if (userEntity == null) {
            userEntity = new UserEntity();
            userEntity.setPenName("高天驰");
            userEntity.setUsername("6159984@gmail.com");
            userEntity.setTimeZone(TimeZone.getDefault());
            userEntity.setProfile("目前在追剧《流人》，每周末晚上喜欢看非凡-迪安的绝地求生直播，目前在积极地找工作。");
            userRepo.save(userEntity);
        }
    }
}
