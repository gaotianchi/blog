package com.gaotianchi.resource.persistence.entity;

import com.gaotianchi.resource.persistence.enums.ArticleStatus;
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
    @Column(columnDefinition = "TEXT")
    private String body;
    @Column(columnDefinition = "TEXT")
    private String summary;
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;
    private String slug;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime publishDatetime;
    private OffsetDateTime updateDatetime;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private SeriesEntity series;

    @ManyToMany(mappedBy = "articleList")
    private Collection<TagEntity> tagList = new ArrayList<>();

    @ManyToMany(mappedBy = "articleList")
    private Collection<IllustrationEntity> illustrationList = new ArrayList<>();

    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Collection<CommentEntity> commentList = new ArrayList<>();
}
