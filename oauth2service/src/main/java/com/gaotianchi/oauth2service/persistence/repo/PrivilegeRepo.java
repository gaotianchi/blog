package com.gaotianchi.oauth2service.persistence.repo;

import com.gaotianchi.oauth2service.persistence.entity.PrivilegeEntity;
import com.gaotianchi.oauth2service.persistence.enums.PrivilegeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo extends JpaRepository<PrivilegeEntity, Long> {
    PrivilegeEntity findByPrivilegeType(PrivilegeType privilegeType);
}
