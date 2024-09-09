package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<ImageEntity, Long> {
}
