package com.gaotianchi.auth.persistence.repo;

import com.gaotianchi.auth.persistence.entity.PrivilegeEntity;
import com.gaotianchi.auth.persistence.enums.PrivilegeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo extends JpaRepository<PrivilegeEntity, Long> {
    PrivilegeEntity findByPrivilegeType(PrivilegeType privilegeType);
}
