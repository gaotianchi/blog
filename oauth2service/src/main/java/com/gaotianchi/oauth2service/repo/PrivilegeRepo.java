package com.gaotianchi.authorizationservice.repo;

import com.gaotianchi.authorizationservice.entity.PrivilegeEntity;
import com.gaotianchi.authorizationservice.enums.PrivilegeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo extends JpaRepository<PrivilegeEntity, Long> {
    PrivilegeEntity findByPrivilegeType(PrivilegeType privilegeType);
}
