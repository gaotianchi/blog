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
    private OffsetDateTime creationDate;
    private OffsetDateTime lastUpdatedDate;
    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus;

    @ManyToOne
    private Series series;

    @ManyToMany(mappedBy = "articles")
    private Collection<TagEntity> tags;

    @OneToOne
    private ImageEntity cover;

    @ManyToOne
    private UserEntity author;

    @OneToMany
    private Collection<ArticleVoteRecordEntity> articleVoteRecordEntities;

    @OneToMany
    private Collection<CommentEntity> commentEntities;

    @OneToMany
    private Collection<ImageEntity> articleImages;
}
