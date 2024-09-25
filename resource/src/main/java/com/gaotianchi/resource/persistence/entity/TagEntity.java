package com.gaotianchi.resource.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Setter
@Getter
public class TagEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private OffsetDateTime creationDatetime;

    @ManyToMany
    private Collection<ArticleEntity> articleList = new ArrayList<>();
}
