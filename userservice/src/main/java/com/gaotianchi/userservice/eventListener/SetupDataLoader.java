package com.gaotianchi.userservice.eventListener;

import com.gaotianchi.userservice.enums.PrivilegeType;
import com.gaotianchi.userservice.enums.RegistrationMethod;
import com.gaotianchi.userservice.enums.RoleType;
import com.gaotianchi.userservice.persistence.entity.Privilege;
import com.gaotianchi.userservice.persistence.entity.Role;
import com.gaotianchi.userservice.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gaotianchi.userservice.persistence.dao.*;

import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepo userRepo;
    private boolean alreadySetup = false;
    private final PrivilegeRepo privilegeRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public SetupDataLoader(PrivilegeRepo privilegeRepo, RoleRepo roleRepo, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.privilegeRepo = privilegeRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
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

        final List<Privilege> notActivatedPrivileges = new ArrayList<>(Collections.singletonList(privilegeLikeOrDislikeContent));
        final List<Privilege> activatedPrivileges = new ArrayList<>(Arrays.asList(privilegeCommentContent, privilegeLikeOrDislikeContent));
        final List<Privilege> bloggerPrivileges = new ArrayList<>(Arrays.asList(privilegeManageContent, privilegeCommentContent, privilegeLikeOrDislikeContent));

        createRoleIfNotFound(RoleType.NOT_ACTIVATED_SUBSCRIBER, notActivatedPrivileges);
        createRoleIfNotFound(RoleType.ACTIVATED_SUBSCRIBER, activatedPrivileges);
        final Role blogger = createRoleIfNotFound(RoleType.BLOGGER, bloggerPrivileges);
        createBloggerIfNotFound("6159984@gmail.com", "6159984@gmail.com", "高天驰", RegistrationMethod.EMAIL, TimeZone.getTimeZone("Asia/Shanghai"), new ArrayList<>(Collections.singleton(blogger)));

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
    @Transactional
    public void createBloggerIfNotFound(final String email, final String password, final String penName, final RegistrationMethod registrationMethod, final TimeZone timeZone, final Collection<Role> roles) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setPenName(penName);
            user.setRegistrationMethod(registrationMethod);
            user.setTimeZone(timeZone);
        }
        user.setRoles(roles);
        userRepo.save(user);
    }
}
