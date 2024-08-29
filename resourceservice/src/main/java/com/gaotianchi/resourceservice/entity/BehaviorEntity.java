package com.gaotianchi.resourceservice.entity;

import com.gaotianchi.resourceservice.enums.BehaviorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BehaviorEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private BehaviorType behavior;
    private Integer storeIncrement;

    public BehaviorEntity(BehaviorType behaviorType) {
        this.behavior = behaviorType;
    }
}
