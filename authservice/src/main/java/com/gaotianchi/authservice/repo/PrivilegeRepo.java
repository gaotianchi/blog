package com.gaotianchi.authservice.repo;

import com.gaotianchi.authservice.entity.PrivilegeEntity;
import com.gaotianchi.authservice.enums.PrivilegeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo extends JpaRepository<PrivilegeEntity, Long> {
    PrivilegeEntity findByPrivilegeType(PrivilegeType privilegeType);
}
