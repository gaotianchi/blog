package com.gaotianchi.resourceservice.repo;

import com.gaotianchi.resourceservice.entity.BehaviorEntity;
import com.gaotianchi.resourceservice.enums.BehaviorType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BehaviorRepo extends JpaRepository<BehaviorEntity, Long> {
    BehaviorEntity findByBehavior(BehaviorType behaviorType);
}
