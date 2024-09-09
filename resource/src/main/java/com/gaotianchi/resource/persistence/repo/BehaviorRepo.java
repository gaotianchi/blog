package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.BehaviorEntity;
import com.gaotianchi.resource.persistence.enums.BehaviorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BehaviorRepo extends JpaRepository<BehaviorEntity, Long> {
    BehaviorEntity findByBehavior(BehaviorType behaviorType);
}
