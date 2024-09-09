package com.gaotianchi.resource.persistence.enums;

import lombok.Getter;

@Getter
public enum BehaviorType {
    READ_AN_ARTICLE(10),
    COMMENT_ON_AN_CONTENT(15),
    VOTE_FOR_AN_CONTENT(5),
    CONTENT_IS_LIKED(20),
    CONTENT_IS_DISLIKED(-5),
    CONTENT_IS_COMMENTED_ON(15),
    CONTENT_VIOLATION(-25);

    private final Integer defaultScoreIncrement;

    BehaviorType(Integer defaultScoreIncrement) {
        this.defaultScoreIncrement = defaultScoreIncrement;
    }
}
