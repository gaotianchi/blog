package com.gaotianchi.resourceservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private ArticleEntity article;

    @OneToOne
    private SeriesEntity series;
}
