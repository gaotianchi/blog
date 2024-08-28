package com.gaotianchi.userservice.persistence.dao;

import com.gaotianchi.userservice.enums.RoleType;
import com.gaotianchi.userservice.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRoleType(RoleType roleType);
}
