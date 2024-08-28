package com.gaotianchi.oauth2service.repo;

import com.gaotianchi.oauth2service.entity.PrivilegeEntity;
import com.gaotianchi.oauth2service.enums.PrivilegeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo extends JpaRepository<PrivilegeEntity, Long> {
    PrivilegeEntity findByPrivilegeType(PrivilegeType privilegeType);
}
