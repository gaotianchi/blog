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
    private Integer score;

    @OneToOne
    private ImageEntity avatar;

    @OneToMany
    private Collection<ArticleEntity> articleEntities;

    @OneToMany
    private Collection<CommentEntity> commentEntities;

    @OneToMany
    private Collection<ArticleVoteRecordEntity> voteEntities;

    @OneToMany
    private Collection<ImageEntity> imageEntities;

    @OneToMany
    private Collection<AccessRecordEntity> accessRecordEntities;
}
