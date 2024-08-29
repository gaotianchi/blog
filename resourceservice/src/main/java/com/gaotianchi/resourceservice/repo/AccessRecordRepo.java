package com.gaotianchi.resourceservice.repo;

import com.gaotianchi.resourceservice.entity.AccessRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRecordRepo extends JpaRepository<AccessRecordEntity, Long> {
}
