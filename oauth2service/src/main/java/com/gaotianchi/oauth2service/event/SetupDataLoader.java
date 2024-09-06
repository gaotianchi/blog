package com.gaotianchi.oauth2service.event;

import com.gaotianchi.oauth2service.persistence.entity.PrivilegeEntity;
import com.gaotianchi.oauth2service.persistence.entity.RoleEntity;
import com.gaotianchi.oauth2service.persistence.entity.UserEntity;
import com.gaotianchi.oauth2service.persistence.enums.AccountStatus;
import com.gaotianchi.oauth2service.persistence.enums.PrivilegeType;
import com.gaotianchi.oauth2service.persistence.enums.RoleType;
import com.gaotianchi.oauth2service.persistence.repo.PrivilegeRepo;
import com.gaotianchi.oauth2service.persistence.repo.RoleRepo;
import com.gaotianchi.oauth2service.persistence.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final PrivilegeRepo privilegeRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Autowired
    public SetupDataLoader(PrivilegeRepo privilegeRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.privilegeRepo = privilegeRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

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
