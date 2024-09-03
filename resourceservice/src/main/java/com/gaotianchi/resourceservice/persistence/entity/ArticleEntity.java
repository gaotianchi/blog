package com.gaotianchi.resourceservice.persistence.entity;

import com.gaotianchi.resourceservice.persistence.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class ArticleEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String body;
    private String summary;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime publishDatetime;
    private OffsetDateTime lastUpdatedDatetime;
    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus;
    private String slug;


    @ManyToOne
    private SeriesEntity seriesEntity;

    @ManyToMany(mappedBy = "articles")
    private Collection<TagEntity> tags = new ArrayList<>();

    @OneToOne
    private ImageEntity cover;

    @ManyToOne
    private UserEntity author;

    @OneToMany(mappedBy = "target")
    private Collection<ArticleVoteRecordEntity> articleVoteRecordEntities;

    @OneToMany(mappedBy = "article")
    private Collection<CommentEntity> commentEntities;

    @OneToMany(mappedBy = "article")
    private Collection<ImageEntity> articleImages;
}
