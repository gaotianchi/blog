package com.gaotianchi.oauth2service.service;

import com.gaotianchi.oauth2service.entity.RoleEntity;
import com.gaotianchi.oauth2service.entity.UserEntity;
import com.gaotianchi.oauth2service.enums.AccountStatus;
import com.gaotianchi.oauth2service.enums.RoleType;
import com.gaotianchi.oauth2service.repo.PrivilegeRepo;
import com.gaotianchi.oauth2service.repo.RoleRepo;
import com.gaotianchi.oauth2service.repo.UserRepo;
import com.gaotianchi.oauth2service.web.dto.EmailUpdatedMessage;
import com.gaotianchi.oauth2service.web.dto.UserDto;
import com.gaotianchi.oauth2service.web.error.EmailAlreadyExistsException;
import com.gaotianchi.oauth2service.web.error.UserExistingException;
import com.gaotianchi.oauth2service.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Autowired
    public UserDetailsService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo, PrivilegeRepo privilegeRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
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
        Collection<RoleEntity> roleEntities = new ArrayList<>();
        for (String roleName : userDto.getRoles()) {
            RoleType roleType = RoleType.valueOf(roleName);
            RoleEntity roleEntity = roleRepo.findByRoleType(roleType);
            roleEntities.add(roleEntity);
        }
        userEntity.setRoles(roleEntities);
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
        user.setAccountStatus(AccountStatus.DEREGISTERED);
        userRepo.save(user);
    }

    public void lockAccount(Long userId) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        if (userEntity.isEmpty()) throw new UserNotFoundException();
        UserEntity user = userEntity.get();
        user.setAccountStatus(AccountStatus.LOCKED);
        user.setLockedUntil(OffsetDateTime.now().plusDays(7));
        userRepo.save(user);
    }
}
