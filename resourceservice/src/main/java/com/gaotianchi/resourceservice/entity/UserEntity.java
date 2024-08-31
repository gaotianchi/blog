package com.gaotianchi.resourceservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.TimeZone;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String penName;
    private TimeZone timeZone;
    private Integer score = 0;

    @OneToOne
    private AvatarEntity avatarEntity;

    @OneToMany
    private Collection<ArticleEntity> articleEntities;

    @OneToMany
    private Collection<CommentEntity> commentEntities;

    @OneToMany
    private Collection<ArticleVoteRecordEntity> voteEntities;

    @OneToMany
    private Collection<AccessRecordEntity> accessRecordEntities;

    @ManyToOne
    private LevelEntity level;
}
