package com.gaotianchi.authorizationservice.repo;

import com.gaotianchi.authorizationservice.entity.RoleEntity;
import com.gaotianchi.authorizationservice.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRoleType(RoleType roleType);
}
