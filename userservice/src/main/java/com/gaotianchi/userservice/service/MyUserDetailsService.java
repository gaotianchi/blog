package com.gaotianchi.userservice.service;

import com.gaotianchi.userservice.enums.RegistrationMethod;
import com.gaotianchi.userservice.enums.RoleType;
import com.gaotianchi.userservice.persistence.dao.RoleRepo;
import com.gaotianchi.userservice.persistence.dao.UserRepo;
import com.gaotianchi.userservice.persistence.entity.User;
import com.gaotianchi.userservice.web.dto.RegistrationDto;
import com.gaotianchi.userservice.web.error.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Autowired
    public MyUserDetailsService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public User registerNewUserAccount(RegistrationDto registrationDto) {
        if (EmailExists(registrationDto.getEmail())) {
            throw new UserAlreadyExistException("账号" + registrationDto.getEmail() + "已经被注册。");
        }
        final User user = new User();
        user.setPenName(registrationDto.getPenName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRegistrationMethod(registrationDto.getRegistrationMethod());
        if (registrationDto.getRegistrationMethod() == RegistrationMethod.EMAIL) {
            user.setRoles(Collections.singletonList(roleRepo.findByRoleType(RoleType.NOT_ACTIVATED_SUBSCRIBER)));
        } else {
            user.setRoles(Collections.singletonList(roleRepo.findByRoleType(RoleType.ACTIVATED_SUBSCRIBER)));
        }
        return user;
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public boolean EmailExists(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("无法找到用户");
        }

        return new MyUserDetails(user);
    }
}
