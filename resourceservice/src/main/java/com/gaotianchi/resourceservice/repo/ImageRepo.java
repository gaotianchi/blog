package com.gaotianchi.resourceservice.repo;

import com.gaotianchi.resourceservice.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<ImageEntity, Long> {
}
