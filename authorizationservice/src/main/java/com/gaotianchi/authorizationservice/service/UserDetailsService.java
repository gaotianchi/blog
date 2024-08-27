package com.gaotianchi.authorizationservice.service;

import com.gaotianchi.authorizationservice.entity.UserEntity;
import com.gaotianchi.authorizationservice.repo.UserRepo;
import com.gaotianchi.authorizationservice.web.dto.UserDto;
import com.gaotianchi.authorizationservice.web.error.UserExistingException;
import com.gaotianchi.authorizationservice.web.error.UserHasBeenDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username);
    }

    public UserEntity registerNowUserAccount(UserDto userDto) throws UserExistingException {
        if (emailExists(userDto.getEmail())) {
            throw new UserExistingException();
        }
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setLockedUntil(userDto.getLockedUntil());
        userEntity.setRole(userDto.getRole());
        userRepo.save(userEntity);
        return userEntity;
    }

    public boolean emailExists(String email) {
        return userRepo.findByEmail(email) != null;
    }

    public boolean idExists(Long userId) {
        return userRepo.findById(userId).isPresent();
    }

    public boolean deleteUserById(Long userId) {
        if (!idExists(userId)) return false;
        userRepo.deleteById(userId);
        return true;
    }
}
