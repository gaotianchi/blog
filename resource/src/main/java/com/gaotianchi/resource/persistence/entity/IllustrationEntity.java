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
    // shallow data
    private Long id;
    private String filename;
    private String title;
    private String alt;
    private String url;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime updateDatetime;

    // depth
    @ManyToOne
    private UserEntity user;

    @ManyToMany
    private Collection<ArticleEntity> articleList = new ArrayList<>();
}
