package com.gaotianchi.userservice.event;

import com.gaotianchi.userservice.persistence.dao.PrivilegeRepo;
import com.gaotianchi.userservice.persistence.dao.RoleRepo;
import com.gaotianchi.userservice.persistence.dao.UserRepo;
import com.gaotianchi.userservice.persistence.entity.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepo userRepo;
    private boolean alreadySetup = false;
    private final PrivilegeRepo privilegeRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public SetupDataLoader(UserRepo userRepo, PrivilegeRepo privilegeRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.privilegeRepo = privilegeRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
