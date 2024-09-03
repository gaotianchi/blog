package com.gaotianchi.resourceservice.event;

import com.gaotianchi.resourceservice.persistence.entity.*;
import com.gaotianchi.resourceservice.persistence.enums.*;
import com.gaotianchi.resourceservice.persistence.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final LevelRepo levelRepo;
    private final BehaviorRepo behaviorRepo;
    private final PrivilegeRepo privilegeRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Autowired
    public SetupDataLoader(LevelRepo levelRepo, BehaviorRepo behaviorRepo, PrivilegeRepo privilegeRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.levelRepo = levelRepo;
        this.behaviorRepo = behaviorRepo;
        this.privilegeRepo = privilegeRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
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
        PrivilegeEntity postManagement = createPrivilegeIfNotFound(PrivilegeType.POST_MANAGEMENT);
        PrivilegeEntity commentManagement = createPrivilegeIfNotFound(PrivilegeType.COMMENT_MANAGEMENT);
        PrivilegeEntity userManagement = createPrivilegeIfNotFound(PrivilegeType.USER_MANAGEMENT);
        PrivilegeEntity siteManagement = createPrivilegeIfNotFound(PrivilegeType.SITE_MANAGEMENT);
        PrivilegeEntity commenting = createPrivilegeIfNotFound(PrivilegeType.COMMENTING);
        PrivilegeEntity voting = createPrivilegeIfNotFound(PrivilegeType.VOTING);

        RoleEntity bloggerRole = createRoleIfNotFound(RoleType.BLOGGER, List.of(postManagement, commentManagement, userManagement, siteManagement));
        RoleEntity subscriberRole = createRoleIfNotFound(RoleType.SUBSCRIBER, List.of(commenting, voting));

        createBlogger(List.of(bloggerRole, subscriberRole));

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

    private PrivilegeEntity createPrivilegeIfNotFound(PrivilegeType privilegeType) {
        PrivilegeEntity privilegeEntity = privilegeRepo.findByPrivilegeType(privilegeType);
        if (privilegeEntity == null) {
            privilegeEntity = new PrivilegeEntity(privilegeType);
            privilegeRepo.save(privilegeEntity);
        }
        return privilegeEntity;
    }

    private RoleEntity createRoleIfNotFound(RoleType roleType, Collection<PrivilegeEntity> privilegeEntities) {
        RoleEntity roleEntity = roleRepo.findByRoleType(roleType);
        if (roleEntity == null) {
            roleEntity = new RoleEntity(roleType);
            roleEntity.setPrivileges(privilegeEntities);
            roleRepo.save(roleEntity);
        }
        return roleEntity;
    }

    private void createBlogger(List<RoleEntity> roleEntities) {
        UserEntity blogger = userRepo.findByEmail("6159984@gmail.com");
        if (blogger == null) {
            blogger = new UserEntity();
            blogger.setEmail("6159984@gmail.com");
            blogger.setAccountStatus(AccountStatus.ACTIVATED);
            blogger.setPassword(passwordEncoder.encode("{noop}password"));
            blogger.setRoles(roleEntities);
            userRepo.save(blogger);
        }
    }

}
