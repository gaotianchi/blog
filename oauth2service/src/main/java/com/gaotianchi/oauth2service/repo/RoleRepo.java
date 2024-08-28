package com.gaotianchi.oauth2service.repo;

import com.gaotianchi.oauth2service.entity.RoleEntity;
import com.gaotianchi.oauth2service.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRoleType(RoleType roleType);
}
