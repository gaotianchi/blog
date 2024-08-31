package com.gaotianchi.resourceservice.entity;

import com.gaotianchi.resourceservice.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
public class ArticleEntity implements Content {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String body;
    private String Summary;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime publishDatetime;
    private OffsetDateTime lastUpdatedDatetime;
    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus;
    private String slug;


    @ManyToOne
    private SeriesEntity seriesEntity;

    @ManyToMany(mappedBy = "articles")
    private Collection<TagEntity> tags;

    @OneToOne
    private ArticleImageEntity cover;

    @ManyToOne
    private UserEntity author;

    @OneToMany
    private Collection<ArticleVoteRecordEntity> articleVoteRecordEntities;

    @OneToMany(mappedBy = "article")
    private Collection<CommentEntity> commentEntities;

    @OneToMany(mappedBy = "article")
    private Collection<ArticleImageEntity> articleImages;
}
