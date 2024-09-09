package com.gaotianchi.auth.web.service;

import com.gaotianchi.auth.persistence.entity.RoleEntity;
import com.gaotianchi.auth.persistence.entity.UserEntity;
import com.gaotianchi.auth.persistence.enums.RoleType;
import com.gaotianchi.auth.persistence.repo.RoleRepo;
import com.gaotianchi.auth.persistence.repo.UserRepo;
import com.gaotianchi.auth.web.error.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntityFounderService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public EntityFounderService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    private <T> T findOrThrow(Optional<T> entity, String entityName, Long id) throws EntityNotFoundException {
        return entity.orElseThrow(() -> new EntityNotFoundException(entityName + " " + id));
    }

    private <T> T findByIdOrNotFound(JpaRepository<T, Long> repo, Long id, String entityName) throws EntityNotFoundException {
        return findOrThrow(repo.findById(id), entityName, id);
    }

    public boolean userExists(String username) {
        try {
            getUserOrNotFound(username);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    public UserEntity getUserOrNotFound(Long id) throws EntityNotFoundException {
        return findByIdOrNotFound(userRepo, id, "User");
    }

    public UserEntity getUserOrNotFound(String username) throws EntityNotFoundException {
        return Optional.ofNullable(userRepo.findByUsername(username))
                .orElseThrow(() -> new EntityNotFoundException("User " + username));
    }

    public RoleEntity getRoleOrNotFound(RoleType roleType) throws EntityNotFoundException {
        return Optional.ofNullable(roleRepo.findByRoleType(roleType))
                .orElseThrow(() -> new EntityNotFoundException("Role " + roleType.name()));
    }
}
