package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepo extends JpaRepository<AvatarEntity, Long> {
}
