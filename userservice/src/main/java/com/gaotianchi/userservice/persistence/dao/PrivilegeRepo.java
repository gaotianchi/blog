package com.gaotianchi.userservice.persistence.dao;

import com.gaotianchi.userservice.persistence.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo extends JpaRepository<Privilege, Long> {
}
