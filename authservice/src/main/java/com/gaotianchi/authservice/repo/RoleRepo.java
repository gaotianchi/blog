package com.gaotianchi.authservice.repo;

import com.gaotianchi.authservice.entity.RoleEntity;
import com.gaotianchi.authservice.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRoleType(RoleType roleType);
}
