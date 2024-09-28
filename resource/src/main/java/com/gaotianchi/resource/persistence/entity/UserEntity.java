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
    // shallow data
    private Long id;
    private String penName;
    @Column(length = 1000)
    private String profile;
    private TimeZone timeZone;

    // private data
    private String username;

    // depth data
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private AvatarEntity avatar;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Collection<ArticleEntity> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Collection<IllustrationEntity> illustrationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Collection<SeriesEntity> seriesList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Collection<SeriesCoverEntity> seriesCoverList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Collection<CommentEntity> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Collection<AccessRecordEntity> accessRecordList = new ArrayList<>();
}
