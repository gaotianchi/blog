package com.gaotianchi.resourceservice.entity;

import com.gaotianchi.resourceservice.enums.CommentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
public class CommentEntity implements Content {
    @Id
    @GeneratedValue
    private Long id;
    private String body;
    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime lastUpdatedDatetime;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private CommentEntity parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private Collection<CommentEntity> replies;

    @ManyToOne
    private ArticleEntity article;

    @OneToMany
    private Collection<CommentVoteEntity> commentVoteEntities;
}
