package com.gaotianchi.resourceservice.enums;

import lombok.Getter;

@Getter
public enum LevelEnums {
    SEED(10),
    SAPLING(100),
    YOUNG_TREE(1000),
    MATURE_TREE(5000),
    FOREST(100000),
    NATURE(100000);

    private final Integer defaultScoreMilestones;

    LevelEnums(Integer defaultScoreMilestones) {
        this.defaultScoreMilestones = defaultScoreMilestones;
    }
}
