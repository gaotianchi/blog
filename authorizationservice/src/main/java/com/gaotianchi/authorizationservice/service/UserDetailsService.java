package com.gaotianchi.authorizationservice.service;

import com.gaotianchi.authorizationservice.entity.UserEntity;
import com.gaotianchi.authorizationservice.repo.UserRepo;
import com.gaotianchi.authorizationservice.web.dto.EmailUpdatedMessage;
import com.gaotianchi.authorizationservice.web.dto.UserDto;
import com.gaotianchi.authorizationservice.web.error.EmailAlreadyExistsException;
import com.gaotianchi.authorizationservice.web.error.UserExistingException;
import com.gaotianchi.authorizationservice.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

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

    public boolean userIdExists(Long userId) {
        return userRepo.findById(userId).isPresent();
    }

    public boolean deleteUserById(Long userId) {
        if (userIdExists(userId)) return false;
        userRepo.deleteById(userId);
        return true;
    }

    public EmailUpdatedMessage updateUserEmail(Long userId, String newEmail) throws EmailAlreadyExistsException, UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        if (userEntity.isEmpty()) throw new UserNotFoundException();
        if (emailExists(newEmail)) throw new EmailAlreadyExistsException();
        UserEntity user = userEntity.get();
        EmailUpdatedMessage emailUpdatedMessage = new EmailUpdatedMessage();
        emailUpdatedMessage.setOriginalEmail(user.getEmail());
        user.setEmail(newEmail);
        userRepo.save(user);
        emailUpdatedMessage.setCurrentEmail(user.getEmail());
        emailUpdatedMessage.setUpdateData(OffsetDateTime.now());
        return emailUpdatedMessage;
    }

    public void resetPassword(Long userId, String newPassword) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        if (userEntity.isEmpty()) throw new UserNotFoundException();
        UserEntity user = userEntity.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    public void deregister(Long userId) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        if (userEntity.isEmpty()) throw new UserNotFoundException();
        UserEntity user = userEntity.get();
        user.setRole("DEREGISTERED_SUBSCRIBER");
        userRepo.save(user);
    }
}
