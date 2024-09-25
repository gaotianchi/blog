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
public class IllustrationEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String filename;
    private String title;
    private String alt;
    private String originalUrl;
    private String thumbnailUrl;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime updateDatetime;

    @ManyToOne
    private UserEntity user;

    @ManyToMany
    private Collection<ArticleEntity> articleList = new ArrayList<>();
}
