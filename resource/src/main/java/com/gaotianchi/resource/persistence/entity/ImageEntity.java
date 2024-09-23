package com.gaotianchi.resource.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class ImageEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String alt;
    private String name;
    @Column(length = 1000)
    private String urls;
    private boolean forAvatar = false;
    private boolean forArticle = false;
    private boolean forSeries = false;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime updateDatetime;
    private String title;

    @ManyToOne
    private UserEntity user;

    @ManyToMany
    private Collection<ArticleEntity> articles;

    @OneToMany(mappedBy = "cover")
    private Collection<SeriesEntity> series = new ArrayList<>();

    @OneToMany(mappedBy = "avatar")
    private Collection<UserEntity> userEntities = new ArrayList<>();
}
