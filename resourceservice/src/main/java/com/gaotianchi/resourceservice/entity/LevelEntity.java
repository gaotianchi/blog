package com.gaotianchi.resourceservice.entity;

import com.gaotianchi.resourceservice.enums.LevelEnums;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LevelEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private LevelEnums level;
    private Integer scoreMilestones;
}
