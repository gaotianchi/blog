package com.gaotianchi.resource.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TimeZone;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String penName;
    private TimeZone timeZone;
    private Integer score = 0;
    @Column(length = 1000)
    private String profile;

    @ManyToOne
    private ImageEntity avatar;

    @OneToMany(mappedBy = "author")
    private Collection<ArticleEntity> articleEntities = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Collection<SeriesEntity> seriesEntities = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private Collection<CommentEntity> commentEntities = new ArrayList<>();

    @OneToMany(mappedBy = "voter")
    private Collection<ArticleVoteRecordEntity> voteEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private Collection<AccessRecordEntity> accessRecordEntities = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Collection<ImageEntity> imageEntities = new ArrayList<>();

    @ManyToOne
    private LevelEntity level;
}
