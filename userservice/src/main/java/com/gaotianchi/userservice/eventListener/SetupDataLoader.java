package com.gaotianchi.userservice.eventListener;

import com.gaotianchi.userservice.enums.PrivilegeType;
import com.gaotianchi.userservice.enums.RoleType;
import com.gaotianchi.userservice.persistence.entity.Privilege;
import com.gaotianchi.userservice.persistence.entity.Role;
import com.gaotianchi.userservice.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gaotianchi.userservice.persistence.dao.*;

import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    private final PrivilegeRepo privilegeRepo;
    private final RoleRepo roleRepo;
    @Autowired
    public SetupDataLoader(PrivilegeRepo privilegeRepo, RoleRepo roleRepo) {
        this.privilegeRepo = privilegeRepo;
        this.roleRepo = roleRepo;
    }
    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        final Privilege privilegeManageContent = createPrivilegeIfNotFound(PrivilegeType.MANAGEMENT_CONTENT);
        final Privilege privilegeCommentContent = createPrivilegeIfNotFound(PrivilegeType.COMMENT_CONTENT);
        final Privilege privilegeLikeOrDislikeContent = createPrivilegeIfNotFound(PrivilegeType.LIKE_OR_DISLIKE_CONTENT);

        final List<Privilege> bloggerPrivileges = new ArrayList<>(Arrays.asList(privilegeManageContent, privilegeCommentContent, privilegeLikeOrDislikeContent));
        final List<Privilege> notActivatedPrivileges = new ArrayList<>(Collections.singletonList(privilegeLikeOrDislikeContent));
        final List<Privilege> activatedPrivileges = new ArrayList<>(Arrays.asList(privilegeCommentContent, privilegeLikeOrDislikeContent));
        final List<Privilege> lockedPrivileges = new ArrayList<>(Collections.singletonList(privilegeLikeOrDislikeContent));
        final List<Privilege> deregisterPrivileges = new ArrayList<>(Collections.singletonList(privilegeLikeOrDislikeContent));

        createRoleIfNotFound(RoleType.NOT_ACTIVATED_SUBSCRIBER, notActivatedPrivileges);
        createRoleIfNotFound(RoleType.ACTIVATED_SUBSCRIBER, activatedPrivileges);
        createRoleIfNotFound(RoleType.LOCKED_SUBSCRIBER, lockedPrivileges);
        createRoleIfNotFound(RoleType.DEREGISTERED_SUBSCRIBER, deregisterPrivileges);
        final Role blogger = createRoleIfNotFound(RoleType.BLOGGER, bloggerPrivileges);

        alreadySetup = true;
    }
    @Transactional
    public Privilege createPrivilegeIfNotFound(final PrivilegeType privilegeType) {
        Privilege privilege = privilegeRepo.findByPrivilegeType(privilegeType);
        if (privilege == null) {
            privilege = new Privilege(privilegeType);
            privilege = privilegeRepo.save(privilege);
        }
        return privilege;
    }
    @Transactional
    public Role createRoleIfNotFound(final RoleType roleType, final Collection<Privilege> privileges) {
        Role role = roleRepo.findByRoleType(roleType);
        if (role == null) {
            role = new Role(roleType);
        }
        role.setPrivileges(privileges);
        role = roleRepo.save(role);
        return role;
    }
//    @Transactional
//    public User createUserIfNotFound(final String email, final String firstName, final String lastName, final String password, final Collection<Role> roles) {
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            user = new User();
//            user.setFirstName(firstName);
//            user.setLastName(lastName);
//            user.setPassword(passwordEncoder.encode(password));
//            user.setEmail(email);
//            user.setEnabled(true);
//        }
//        user.setRoles(roles);
//        user = userRepository.save(user);
//        return user;
//    }
}
