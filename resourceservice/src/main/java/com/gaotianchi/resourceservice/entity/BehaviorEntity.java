package com.gaotianchi.resourceservice.entity;

import com.gaotianchi.resourceservice.enums.BehaviorEnums;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BehaviorEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private BehaviorEnums behavior;
    private Integer storeIncrement;
}
