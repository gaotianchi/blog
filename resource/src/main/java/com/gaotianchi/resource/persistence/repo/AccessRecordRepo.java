package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.AccessRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRecordRepo extends JpaRepository<AccessRecordEntity, Long> {
}
