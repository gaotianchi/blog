package com.gaotianchi.resourceservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class ImageEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String fileDirName;
    private String fileExtension;
    private String originalUrl;
    private String thumbnailUrl;
    private String alt;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private ArticleEntity article;

    @OneToMany(mappedBy = "cover")
    private Collection<SeriesEntity> series = new ArrayList<>();

    @OneToMany(mappedBy = "avatar")
    private Collection<UserEntity> userEntities = new ArrayList<>();
}
