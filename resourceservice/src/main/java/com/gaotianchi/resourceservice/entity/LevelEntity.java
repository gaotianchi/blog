package com.gaotianchi.resourceservice.entity;

import com.gaotianchi.resourceservice.enums.LevelType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LevelEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private LevelType level;
    private Integer scoreMilestones;

    public LevelEntity(LevelType level) {
        this.level = level;
    }
}
