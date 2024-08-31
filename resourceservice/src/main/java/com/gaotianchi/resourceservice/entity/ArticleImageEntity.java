package com.gaotianchi.resourceservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ArticleImageEntity {

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
}
