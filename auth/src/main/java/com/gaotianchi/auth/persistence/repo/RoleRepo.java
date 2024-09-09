package com.gaotianchi.auth.persistence.repo;

import com.gaotianchi.auth.persistence.entity.RoleEntity;
import com.gaotianchi.auth.persistence.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRoleType(RoleType roleType);
}
