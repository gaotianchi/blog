package com.gaotianchi.resource.persistence.entity;

import com.gaotianchi.resource.persistence.enums.CommentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
public class CommentEntity {
    @Id
    @GeneratedValue
    // shallow data
    private Long id;
    private String body;
    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime lastUpdatedDatetime;

    // depth data
    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Collection<CommentEntity> replyList;

    @ManyToOne
    private ArticleEntity article;
}
