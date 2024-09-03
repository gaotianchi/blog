package com.gaotianchi.resourceservice.persistence.repo;

import com.gaotianchi.resourceservice.persistence.entity.AccessRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRecordRepo extends JpaRepository<AccessRecordEntity, Long> {
}
