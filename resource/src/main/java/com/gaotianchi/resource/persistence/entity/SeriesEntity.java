package com.gaotianchi.resource.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Setter
@Getter
public class SeriesEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String profile;
    private OffsetDateTime creationDatetime;

    @OneToOne
    private SeriesCoverEntity cover;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "series")
    private Collection<ArticleEntity> articleList = new ArrayList<>();
}
