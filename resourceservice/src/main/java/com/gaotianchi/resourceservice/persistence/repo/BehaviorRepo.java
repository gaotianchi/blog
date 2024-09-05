package com.gaotianchi.resourceservice.persistence.repo;

import com.gaotianchi.resourceservice.persistence.entity.BehaviorEntity;
import com.gaotianchi.resourceservice.persistence.enums.BehaviorType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BehaviorRepo extends JpaRepository<BehaviorEntity, Long> {
    BehaviorEntity findByBehavior(BehaviorType behaviorType);
}
